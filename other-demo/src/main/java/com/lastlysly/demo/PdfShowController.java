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
import java.net.URLEncoder;

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
//        //强制浏览器下载
//        response.setHeader("content-disposition", "attachment;filename=" + realName);
//
//        //浏览器尝试打开,支持office online或浏览器预览pdf功能
//        response.setHeader("content-disposition", "inline;filename=" + realName);

        response.setHeader("content-disposition","attachment");
//        response.setHeader("content-disposition","inline");

        String path = "C:\\Users\\lastlySly\\Desktop\\20190919\\1.png";
        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File(path));
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void downloadFileByFileId(@PathVariable("fileId") String fileId, HttpServletRequest request,
                                     HttpServletResponse response){
        response.setHeader("content-type","application/octet-stream");
        response.setContentType("application/octet-stream");
        String path = "文件路径/1.png";
        try {
            String fileName = "文件名";

            /**
             * 处理文件名中文乱码
             */
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                // firefox浏览器
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                // IE浏览器
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
                // 谷歌
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition","attachment;filename=" + fileName + "." + "jpg");
            byte[] bytes = FileUtils.readFileToByteArray(new File(path));
            response.getOutputStream().write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
