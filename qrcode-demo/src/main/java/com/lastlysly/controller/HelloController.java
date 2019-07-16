package com.lastlysly.controller;

import com.lastlysly.utils.QRCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-03-01 17:03
 **/
@RestController
public class HelloController {


    @GetMapping("/showQRCode")
    public void showQRCode(HttpServletResponse response) throws Exception {

        int width = 100; // 二维码图片的宽
        int height = 100; // 二维码图片的高
        String format = "png"; // 二维码图片的格式
        String text = "http://lastlysly.cn/uploadpic/1.jpg";
        System.out.println("开始生成二维码");
        QRCodeUtil.generateQRCode(text,width,height,format,response);
        System.out.println("生成二维码成功");

        
//        response.getOutputStream();
    }

    @GetMapping("/sayHello")
    public String sayHello(){
        return "sayHello";
    }


}
