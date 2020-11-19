package com.lastlysly.itext;

import com.google.common.collect.Maps;
import com.itextpdf.text.DocumentException;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-29 10:17
 **/
public class Demo {
    public static void main(String[] args) throws Exception {
        test1();
//        test2();
//        testemplate3();
//
//        Map<String,Object> map1 = Maps.newHashMap();
//        map1.put("name","");
//        map1.put("age","");
//        map1.put("gand","");
//        map1.put("gand2","");
//        Map<String,Object> map2 = Maps.newHashMap();
//        map2.put("name","测试");
//        map2.put("age","12");
//        Map<String,Object> map3 = Maps.newHashMap();
//        map3.put("gand2","gand2");
//
//        map1.putAll(map2);
//        map1.putAll(map3);
//
//        map1.forEach((k,v) -> {
//            System.out.println("k=" + k + "；v=" + v);
//        });
//
//        LocalDateTime date1 = LocalDateTime.of(1995, 10, 6,12,23,10);
//        LocalDateTime now =LocalDateTime.now();
//        int age = now.toLocalDate().until(date1.toLocalDate()).getYears();
//        System.out.println("You're " + age + " years old.");
    }

    public static void testemplate3() throws IOException, DocumentException {
//        String templatePath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\templateTest.pdf";
        String templatePath = "F:\\MyAllWorkProject\\xmgps\\出租汽车服务管理平台\\动态稽查-系统文书模板\\模板\\立案登记表.pdf";
        Map<String,Object> data = Maps.newHashMap();
        data.put("bc_caseSourceType_cb",1);
        data.put("bc_iin_driverIdName","测试");
        data.put("bc_iin_showTest","测你在楠楠楠安安娜娜打你在哪啊算打算大所大大多试");
        data.put("bc_iin_showTime", LocalDateTime.now());
        data.put("name1","消灭");
        data.put("name","姓名");
        data.put("name2","消灭2");
        data.put("name3","消灭3");
        data.put("name4","消灭4");
        data.put("name5","消灭5");
        data.put("age","28");
        data.put("sex","男");
        data.put("id","123456789012345678");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ItextUtils.fillTemplate(data, outputStream, templatePath);
        String topath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\my.pdf";
        File file = new File(topath);
        file.createNewFile();
        FileUtils.writeByteArrayToFile(file,outputStream.toByteArray());
    }

    public static void test2() throws Exception {
        String pdfPath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\1立案登记表.pdf";
        String imagePath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\11.png";
        byte[] bytes = FileUtils.readFileToByteArray(new File(pdfPath));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

//        ItextUtils.addImageWaterMark(bytes,outputStream,bytes,300,200,100);
        String topath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\www.pdf";
        File file = new File(topath);
        file.createNewFile();
        FileUtils.writeByteArrayToFile(file,outputStream.toByteArray());
        outputStream.close();

    }
    public static void test1() throws Exception {
        String pdfPath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\1立案登记表.pdf";
        String imagePath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\章.png";
        String topath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\233.pdf";
        ItextUtils.addImageWaterMark(pdfPath,topath,imagePath,400,150,10);
    }

}
