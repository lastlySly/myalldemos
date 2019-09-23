package com.lastlysly.controller;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.lastlysly.utils.CustomAsposeWordsUtils;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-05 10:40
 **/
@RestController
public class DemoController {

    @GetMapping("hello")
    public String hello(){
        return "hello electronic-contract";
    }


    @RequestMapping("/createAndShowPdf")
    public void createAndShowPdf(HttpServletResponse response){
        Map map = CustomAsposeWordsUtils.getDataMap();
        CustomAsposeWordsUtils.createAndShowPdf(response,map);
    }
}
