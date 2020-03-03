package com.lastlysly.controller;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoControllerTest {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Test
    public void hello() {

        Map<String,Object> map = new HashMap<>();
        map.put("test",233);
        map.put("volumeName",233);
        map.put("ageName","定时拍照");
        try {
            /**
             * 交由spring管理方式
             */
            Template t = freeMarkerConfigurer.getConfiguration().getTemplate("testExcel.ftl");
            File outFile = new File("C:\\Users\\lastlySly\\Desktop\\testExcel1.xlsx");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            t.process(map, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}