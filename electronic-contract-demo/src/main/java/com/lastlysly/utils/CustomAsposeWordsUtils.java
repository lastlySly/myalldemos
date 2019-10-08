package com.lastlysly.utils;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.google.common.collect.Maps;
import com.lastlysly.demo.Demo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-05 10:19
 * AsposeWords 工具类
 **/
@Component
public class CustomAsposeWordsUtils {

    /**
     * 该方式为交由spring管理方式，由于资源文件在打成jar包运行时，
     * 无法读取到jar内部绝对路径，会导致空指针异常，采用该方式初始化freemark的模板则能解决，
     * 需进行配置，详情请看配置文件appliction.properties
     */
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public static void getLicense() throws Exception {
        String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
        ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
        License license = new License();
        license.setLicense(is);
    }


    /**
     * 装配数据
     * @return
     */
    public static Map getDataMap(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("name","小小");
        map.put("age",20);
        map.put("adress","某某某看守所");
        map.put("gand","女");
        return map;
    }


    /**
     * 生成电子合同到指定位置   文档格式
     */
    public void createDocToPath(String savePath,Map map){

//        Map map = getDataMap();
        try {
            /**
             * 普通方式
             */
//            Version version = new Version("2.3.28");
//            Configuration configuration = new Configuration(version);
//            configuration.setDefaultEncoding("utf-8");
//            URI uri = CustomAsposeWordsUtils.class.getResource("/ftl/").toURI();
//            String path = uri.getPath();
//            configuration.setDirectoryForTemplateLoading(new File(path));
//            //以utf-8的编码读取ftl文件
//            Template t =  configuration.getTemplate("test-word-byfeemarker.ftl","utf-8");

            /**
             * 交由spring管理方式
             */
            Template t = freeMarkerConfigurer.getConfiguration().getTemplate("test-word-byfeemarker.ftl");
            File outFile = new File(savePath);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            t.process(map, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
//        catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 生成电子合同到指定位置   转pdf格式
     */
    public void createPdfToPath(String savePath,Map map){
//        Map map = getDataMap();
        ByteArrayOutputStream byteArrayOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {

            /**
             * 返回渲染完数据的word文件的流
             */
            byteArrayOutputStream = getWordFileOutputStream(map);

            /**
             * ================word模板渲染完数据后，转换为其他格式=====================
             */

            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            /**
             * 获取授权，去除水印
             */
            CustomAsposeWordsUtils.getLicense();
            Document document = new Document(byteArrayInputStream);

//            String pdfFilePath = "C:/Users/lastlySly/Desktop/testword.pdf";

            /**
             * 转为pdf并保存到本地
             */
            document.save(new FileOutputStream(new File(savePath)), SaveFormat.PDF);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayOutputStream != null){
                    byteArrayOutputStream.close();
                }
                if (byteArrayInputStream != null){
                    byteArrayInputStream.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 生成电子合同到指定位置   转图片格式
     */
    public void createImageToPath(Map map){
//        Map map = getDataMap();
        ByteArrayOutputStream byteArrayOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {

            /**
             * 传入数据，渲染模板，返回渲染完数据的word文件的流
             */
            byteArrayOutputStream = getWordFileOutputStream(map);

            /**
             * ================word模板渲染完数据后，转换为其他格式=====================
             */

            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            /**
             * 获取授权，去除水印
             */
            CustomAsposeWordsUtils.getLicense();
            Document document = new Document(byteArrayInputStream);

            /**
             * 保存图片到本地
             */
            ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
            iso.setResolution(200);
            for (int page = 0; page < document.getPageCount(); page ++){
                System.out.println("===生成第"+page + "张");
                iso.setPageIndex(page);
                document.save(new FileOutputStream(new File("C:/Users/lastlySly/Desktop/image"+ page +".jpeg")), iso);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayOutputStream != null){
                    byteArrayOutputStream.close();
                }
                if (byteArrayInputStream != null){
                    byteArrayInputStream.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 生成电子合同转为pdf并保存于 输出流
     * @param response
     * @param map
     */
    public void createAndShowPdf(HttpServletResponse response,Map map){

//        Map map = CustomAsposeWordsUtils.getDataMap();
        ByteArrayOutputStream byteArrayOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        ByteArrayInputStream byteArrayInputStream = null;

        try {
            byteArrayOutputStream = getWordFileOutputStream(map);

            /**
             * ================word模板渲染完数据后，转换为其他格式=====================
             */
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            /**
             * 获取授权，去除水印
             */
            CustomAsposeWordsUtils.getLicense();
            Document document = new Document(byteArrayInputStream);

            /**
             * 转为pdf并保存于 输出流
             */
            document.save(response.getOutputStream(), SaveFormat.PDF);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (byteArrayOutputStream != null){
                    byteArrayOutputStream.close();
                }
                if (byteArrayInputStream != null){
                    byteArrayInputStream.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 传入数据，渲染模板，返回渲染完数据的word文件的流
     */
    public ByteArrayOutputStream getWordFileOutputStream(Map map) throws URISyntaxException, IOException, TemplateException {
        ByteArrayOutputStream byteArrayOutputStream;
        OutputStreamWriter outputStreamWriter;
        /**
         * 普通方式
         */
//        Version version = new Version("2.3.28");
//        Configuration configuration = new Configuration(version);
//        configuration.setDefaultEncoding("utf-8");
//        URI uri = Demo.class.getResource("/ftl/").toURI();
//        String path = uri.getPath();
//        configuration.setDirectoryForTemplateLoading(new File(path));
//        //以utf-8的编码读取ftl文件
//        Template template = configuration.getTemplate("test-word-byfeemarker.ftl", "utf-8");
        /**
         * 交由spring管理方式
         */
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("test-word-byfeemarker.ftl","utf-8");
        byteArrayOutputStream = new ByteArrayOutputStream();
        outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
        template.process(map, outputStreamWriter);

        return byteArrayOutputStream;
    }



}
