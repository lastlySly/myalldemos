package com.lastlysly.utils;

import com.aspose.words.*;
import com.google.common.collect.Maps;
import com.lastlysly.demo.Demo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
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
    private static Logger logger = LoggerFactory.getLogger(CustomAsposeWordsUtils.class);
    private static String projectPath = System.getProperty("user.dir");
    static {
        try {
            getLicense();
            setAsposeFolder();
        } catch (Exception e) {
            logger.error("CustomAsposeWordsUtils初始化失败",e);
        }
    }
    /**
     * 该方式为交由spring管理方式，由于资源文件在打成jar包运行时，
     * 无法读取到jar内部绝对路径，会导致空指针异常，采用该方式初始化freemark的模板则能解决，
     * 需进行配置，详情请看配置文件appliction.properties
     */
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 获取授权，去除水印
     * @throws Exception
     */
    public static void getLicense() throws Exception {
        String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
        ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
        License license = new License();
        license.setLicense(is);
    }

    /**
     * 设置字体文件夹(默认扫描系统字体包，linux上可能不存在部分字体，出现被替换为方框)
     * 也可以不设置这句，但需要linux安装这些字体
     */
    public static void setAsposeFolder() throws IOException {
        /**
         * 注册为Component 后无法使用classLoader
         * 因为是通过：ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path); 来初始化所有的类。
         * spring默认的classloader会是自己定义的DefaultResourceLoader，并且会把 DefaultResourceLoader设置当前线程的默认加载器。当你在加载外部类的时候就会找不到类，因为加载外部类是在另一个ClassLoader中。
         */
//        ClassLoader classLoader = CustomAsposeWordsUtils.class.getClassLoader();

        InputStream simheiIs = CustomAsposeWordsUtils.class.getResourceAsStream("/asposefonts/simhei.ttf");
        InputStream simsunIs = CustomAsposeWordsUtils.class.getResourceAsStream("/asposefonts/simsun.ttc");
        File fileDir = new File(projectPath + "/asposefonts");
        File simheiFile = new File(projectPath + "/asposefonts/simhei.ttf");
        IsToFile(simheiFile,fileDir,simheiIs);
        File simsunFile = new File(projectPath + "/asposefonts/simsun.ttc");
        IsToFile(simsunFile,fileDir,simsunIs);
        FontSettings.setFontsFolder(projectPath + "/asposefonts/",false);
    }
    private static void IsToFile(File file,File fileDir,InputStream fontIs) throws IOException {
        if (!file.exists()){
            if (!fileDir.exists()){
                fileDir.mkdirs();
            }
            file.createNewFile();
            FileUtils.copyInputStreamToFile(fontIs,file);
            fontIs.close();
        }

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
            /**
             * 及时关闭流，防止资源占用不释放（详细查看java垃圾回收机制）
             */
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            Writer out = new BufferedWriter(outputStreamWriter);
            t.process(map, out);
            out.close();
            outputStreamWriter.close();
            fileOutputStream.close();
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
            Document document = new Document(byteArrayInputStream);

//            String pdfFilePath = "C:/Users/lastlySly/Desktop/testword.pdf";

            /**
             * 转为pdf并保存到本地
             */
            document.save(savePath, SaveFormat.PDF);

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

            Document document = new Document(byteArrayInputStream);

            /**
             * 保存图片到本地
             */
            ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
            iso.setResolution(200);
            for (int page = 0; page < document.getPageCount(); page ++){
                System.out.println("===生成第"+page + "张");
                iso.setPageIndex(page);
                document.save("C:/Users/lastlySly/Desktop/image"+ page +".jpeg", iso);
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

    public void ss() {
        System.out.println(444);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void aa() {
        ss();
    }
}
