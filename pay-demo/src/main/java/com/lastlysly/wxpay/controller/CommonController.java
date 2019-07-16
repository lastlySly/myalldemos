package com.lastlysly.wxpay.controller;

import com.lastlysly.wxpay.utils.MyResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-02-13 17:12
 **/
@Controller
public class CommonController {

    /**
     * 小程序获取openid
     * @param request
     * @param response
     * @return
     * @throws Exception
     *
     * by  huquanshui
     *
     */
    @RequestMapping(value="getOpenidInXcx")
    @ResponseBody
    public MyResult getOpenidInXcx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?appid=wx84e1ee1cc3d2036a&secret=365065ae73f04318d205fe84a232fc4e&grant_type=authorization_code&js_code="+request.getParameter("js_code"));
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
        conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Cookie", "JSESSIONID=XXXXXXXXXXXXXXXXXXXXX");
        conn.setRequestProperty("Host", "ptlogin2.qq.com");
        conn.setRequestProperty("Referer", "http://www.qq.com");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 Safari/537.31");
        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        conn.connect();         //获取连接
        InputStream is = conn.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        StringBuffer bs = new StringBuffer();
        String l = null;
        while((l=buffer.readLine())!=null){
            bs.append(l);
        }
        return new MyResult(1,"获取openid成功",bs);
    }



    public MyResult getOpenIdInGzh(){
        return null;
    }



}
