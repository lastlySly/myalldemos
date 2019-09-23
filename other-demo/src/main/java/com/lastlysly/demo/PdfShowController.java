package com.lastlysly.demo;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-23 09:00
 * pdf打开新窗口预览
 **/
@Controller
@RequestMapping("/pdf")
public class PdfShowController {

    @GetMapping("/showPdf/{id}")
    public void showPdf(@PathVariable("id") String id, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("content-disposition","attachment");
//        response.setHeader("content-disposition","inline");
//        String path = "C:\\Users\\lastlySly\\Desktop\\新建文件夹\\test\\mytest\\一点课堂-9-zuul整合Swagger2介绍.pdf";
        String path = "C:\\Users\\lastlySly\\Desktop\\20190919\\1.png";
        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File(path));
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
