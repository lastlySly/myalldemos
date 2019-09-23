package com.lastlysly.freemarkerdemo;


import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-08-18 13:12
 **/
@RestController
@RequestMapping("freemarkDemoController")
public class FreemarkDemoController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    @GetMapping("/demo")
    public void demo1(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=demo.doc");
        try {
            Map<String,Object> map = new HashMap<>(16);

            map.put("name","小小");
            map.put("age",20);
            map.put("adress","某某某看守所");
            map.put("gand","女");
            Version version = new Version("2.3.28");
            Configuration configuration = new Configuration(version);
            configuration.setDefaultEncoding("utf-8");
            String path = Demo.class.getResource("/ftl/").getPath();
            System.out.println(path);
            System.out.println("=======================");
            path = "F:/JetBrains/IntelliJ IDEA/javaworkspace/myalldemos/other-demo/target/classes/ftl";
            configuration.setDirectoryForTemplateLoading(new File(path));
            //以utf-8的编码读取ftl文件
            Template t =  configuration.getTemplate("test-word-byfeemarker.ftl","utf-8");
//            Writer out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"));

            OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
            t.process(map, out);



            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/demo2")
    public void demo2(HttpServletResponse response){
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=demo.jpeg");
        InputStream in = null;
        try {
            in = new FileInputStream("C:\\Users\\lastlySly\\Desktop\\testword.doc");
            Document doc2 = new Document(in);
//            Document doc = new Document("C:\\Users\\lastlySly\\Desktop\\testword.doc");
            ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
            iso.setResolution(200);
//            doc.save(os, iso);
            doc2.save(response.getOutputStream(), iso);

            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }






    @GetMapping("/demo3")
    public void demo3(HttpServletRequest request, HttpServletResponse response){
//        response.setHeader("content-type", "application/octet-stream");
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment;filename=demo3.jpeg");
        try {
//            Map<String,Object> map = new HashMap<>(16);
//
//            map.put("name","小小");
//            map.put("age",20);
//            map.put("adress","某某某看守所");
//            map.put("gand","女");



            Map<String,Object> map = new HashMap<>(16);

            map.put("rentContractNo","小小");
            map.put("lessorName","小小");
            map.put("lessorIdentityNumber",202222222);
            map.put("lessorOperator","test");
            map.put("lessorOperatorIdNum","test");
            map.put("renterName","test");
            map.put("renterIdentityNumber","test");
            map.put("driverLicenseType","test");
            map.put("agentName","test");
            map.put("agentIdentityNumber","test");

            map.put("vehiclePlateNumber","某某某看守所");
            map.put("vehicleBrandModel","测试");
            map.put("fuelType","类型");
            map.put("carframe","车架号");
            map.put("engineNo","发动机号");
            map.put("billingPlan","(1)");

            map.put("beginYear","2019");
            map.put("beginMonth","09");
            map.put("beginDay","01");
            map.put("beginHour","01");
            map.put("endYear","2019");
            map.put("endMonth","09");
            map.put("endDay","09");
            map.put("endHour","09");
            map.put("endMinute","09");
            map.put("rentDayNum","09");
            map.put("rentHourNum","09");
            map.put("rentMinuteNum","09");
            map.put("dayRentalB","100");
            map.put("dayRentalS","100");
            map.put("dayRentalY","100");
            map.put("overTimeMoney","100");
            map.put("dayDepositPaid","100");


            map.put("mileage","100");
            map.put("rentalB","100");
            map.put("rentalS","100");
            map.put("rentalY","100");
            map.put("priceMileage","100");
            map.put("depositPaid","100");

            map.put("rentalAddress","100");
            map.put("returnCarAddress","100");

            map.put("lessorPhone","100");
            map.put("renterPhone","100");
            map.put("lessorAddress","100");
            map.put("renterAddress","100");
            map.put("guarantor","100");
            map.put("organcodeOrIdNum","100");
            map.put("guarantorPhone","100");
            map.put("guarantorAddress","100");
            map.put("signedAddress","100");
            map.put("signedYear","100");
            map.put("signedMonth","100");
            map.put("signedDay","100");


            map.put("carNum","100");
            map.put("beginMinute","100");



            Version version = new Version("2.3.28");
            Configuration configuration = new Configuration(version);
            configuration.setDefaultEncoding("utf-8");
            String path = Demo.class.getResource("/ftl/").getPath();
            System.out.println(path);
            System.out.println("=======================");
            path = "F:/JetBrains/IntelliJ IDEA/javaworkspace/myalldemos/other-demo/target/classes/ftl";
            configuration.setDirectoryForTemplateLoading(new File(path));
            //以utf-8的编码读取ftl文件
//            Template t =  configuration.getTemplate("test-word-byfeemarker.ftl","utf-8");
            Template t =  configuration.getTemplate("rentContractTemplate.ftl","utf-8");
//            Writer out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(byteArrayOutputStream);
            t.process(map, out);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());



            getLicense();
//            com.aspose.words.Document document = new com.aspose.words.Document(docPath);


            Document doc2 = new Document(byteArrayInputStream);
//            Document doc = new Document("C:\\Users\\lastlySly\\Desktop\\testword.doc");

            /**
             * 转图片
             */
            ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
            iso.setResolution(200);
//            doc2.save(response.getOutputStream(), iso);

            for (int page = 0; page < doc2.getPageCount(); page ++){
                System.out.println("===生成第"+page + "张");
                iso.setPageIndex(page);
                doc2.save(new FileOutputStream(new File("C:/Users/lastlySly/Desktop/img"+ page +".jpeg")), iso);
            }
            /**
             * 转pdf
             */
//            doc2.save(new FileOutputStream(new File("C:/Users/lastlySly/Desktop/testword4.pdf")),SaveFormat.PDF);


            byteArrayInputStream.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLicense() throws Exception {
        String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
        ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
        License license = new License();
        license.setLicense(is);
    }

}
