package com.lastlysly.freemarkerdemo;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-08-16 17:29
 **/
public class Demo {

    public static void main(String[] args) {
        try {
            Map<String,Object> map = new HashMap<>(16);

            map.put("name","小小");
            map.put("age",20);
            map.put("adress","某某某看守所");
            map.put("gand","女");
            Version version = new Version("2.3.28");
            Configuration configuration = new Configuration(version);
            configuration.setDefaultEncoding("utf-8");
            URI uri = Demo.class.getResource("/ftl/").toURI();
            String path = uri.getPath();

            configuration.setDirectoryForTemplateLoading(new File(path));


            File outFile = new File("C:/Users/lastlySly/Desktop/testword.doc");

            //以utf-8的编码读取ftl文件
            Template t =  configuration.getTemplate("test-word-byfeemarker.ftl","utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            t.process(map, out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
