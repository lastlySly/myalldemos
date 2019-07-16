package com.lastlysly.wxpay.utils;

import com.github.wxpay.sdk.WXPay;
import com.lastlysly.wxpay.config.MyWxPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-02-14 09:32
 **/
@Component
public class MyWxPayUtils {

    @Value("${wxpay.gzhappid}")
    private String gzhAppId;
    @Value("${wxpay.xcxappid}")
    private String xcxAppId;
    @Value("${wxpay.serviceappid}")
    private String serviceAppId;
    @Value("${wxpay.mchid}")
    private String mchId;
    @Value("${wxpay.appkey}")
    private String appKey;
    @Value("${wxpay.usesandbox}")
    private boolean useSandBox;
    @Value("${wxpay.wxnotifyurl}")
    private String wxNotifyUrl;



    /**
     * 构建支付请求对象
     * @param tradeType
     * @return
     */
    public WXPay buildWXPay(String tradeType) throws Exception {
        MyWxPayConfig config = null;
        if (tradeType.equals(WxPayConstant.WXPay.JSAPI)){
            config = new MyWxPayConfig(gzhAppId,mchId,appKey);
        }else if (tradeType.equals(WxPayConstant.WXPay.MWEB)){
            config = new MyWxPayConfig(serviceAppId,mchId,appKey);
        }
        return new WXPay(config,wxNotifyUrl,true,useSandBox);
    }

}


