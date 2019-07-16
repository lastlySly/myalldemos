package com.lastlysly.wxpay.controller;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.lastlysly.wxpay.config.MyWxPayConfig;
import com.lastlysly.wxpay.utils.MyResult;
import com.lastlysly.wxpay.utils.MyWxPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-02-13 17:53
 **/
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private MyWxPayUtils myWxPayUtils;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/test")
    @ResponseBody
    public MyResult test(){

        try {
            WXPay wxPay = myWxPayUtils.buildWXPay("jsapi");
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "腾讯充值中心-QQ会员充值");
            data.put("out_trade_no", "2016090910595900000012");
            data.put("device_info", "");
            data.put("fee_type", "CNY");
            data.put("total_fee", "1");
            data.put("spbill_create_ip", "123.12.12.123");
            data.put("notify_url", "http://www.lastlysly.cn:8081/order/payCallback");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", "12");

            logger.info("发起微信支付下单接口, request={}", data);
            //统一下单
            Map<String, String> resp = wxPay.unifiedOrder(data);
            logger.info("微信支付下单成功, 返回值 response={}", resp);



            String returnCode = resp.get("return_code");
            if (!"SUCCESS".equals(returnCode)) {
                return null;
            }
            String resultCode = resp.get("result_code");
            if (!"SUCCESS".equals(resultCode)) {
                return null;
            }
            String prepay_id = resp.get("prepay_id");
            if (prepay_id == null) {
                return null;
            }




            // ******************************************
            //
            //  前端调起微信支付必要参数
            //
            // ******************************************
            String packages = "prepay_id=" + prepay_id;
            Map<String, String> wxPayMap = new HashMap<String, String>();
            wxPayMap.put("appId", "wx84e1ee1cc3d2036a");
            wxPayMap.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
            wxPayMap.put("nonceStr", UUID.randomUUID().toString().replace("-",""));
            wxPayMap.put("package", packages);
            wxPayMap.put("signType", "MD5");
            // 加密串中包括 appId timeStamp nonceStr package signType 5个参数, 通过sdk WXPayUtil类加密, 注意, 此处使用  MD5加密  方式
            String sign = WXPayUtil.generateSignature(wxPayMap, "rwyywy56871568715687156871568715");

            // ******************************************
            //
            //  返回给前端调起微信支付的必要参数
            //
            // ******************************************
            Map<String, String> result = new HashMap<>();
            result.put("prepay_id", prepay_id);
            result.put("sign", sign);
            result.putAll(wxPayMap);


            return new MyResult(1,"测试统一下单",result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @RequestMapping(value = "/payCallback", method = RequestMethod.POST)
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        logger.info("进入微信支付异步通知");
        String resXml="";
        try{
            //
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml=sb.toString();
            logger.info("微信支付异步通知请求包: {}", resXml);
//            return wxTicketService.payBack(resXml);




            logger.info("payBack() start, notifyData={}", resXml);
            String xmlBack="";
            Map<String, String> notifyMap = null;
            try {
                WXPay wxpay = new WXPay(new MyWxPayConfig("wx123456789","mchid","rwyywy56871568715687156871568715"));

                notifyMap = WXPayUtil.xmlToMap(resXml);         // 转换成map
                if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                    // 签名正确
                    // 进行处理。
                    // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                    String return_code = notifyMap.get("return_code");//状态
                    String out_trade_no = notifyMap.get("out_trade_no");//订单号

                    if (out_trade_no == null) {
                        logger.info("微信支付回调失败订单号: {}", notifyMap);
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                        return xmlBack;
                    }

                    // 业务逻辑处理 ****************************
                    logger.info("微信支付回调成功订单号: {}", notifyMap);
                    xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
                    return xmlBack;
                } else {
                    logger.error("微信支付回调通知签名错误");
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }
            } catch (Exception e) {
                logger.error("微信支付回调通知失败",e);
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            return xmlBack;





        }catch (Exception e){
            logger.error("微信支付回调通知失败",e);
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }

//    @Override
//    public String payBack(String notifyData) {
//        logger.info("payBack() start, notifyData={}", notifyData);
//        String xmlBack="";
//        Map<String, String> notifyMap = null;
//        try {
//            WXPay wxpay = new WXPay(iWxPayConfig);
//
//            notifyMap = WXPayUtil.xmlToMap(notifyData);         // 转换成map
//            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
//                // 签名正确
//                // 进行处理。
//                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
//                String return_code = notifyMap.get("return_code");//状态
//                String out_trade_no = notifyMap.get("out_trade_no");//订单号
//
//                if (out_trade_no == null) {
//                    logger.info("微信支付回调失败订单号: {}", notifyMap);
//                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
//                    return xmlBack;
//                }
//
//                // 业务逻辑处理 ****************************
//                logger.info("微信支付回调成功订单号: {}", notifyMap);
//                xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
//                return xmlBack;
//            } else {
//                logger.error("微信支付回调通知签名错误");
//                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
//                return xmlBack;
//            }
//        } catch (Exception e) {
//            logger.error("微信支付回调通知失败",e);
//            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
//        }
//        return xmlBack;
//    }



}
