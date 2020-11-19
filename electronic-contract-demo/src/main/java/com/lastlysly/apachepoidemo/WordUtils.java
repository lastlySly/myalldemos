package com.lastlysly.apachepoidemo;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-28 17:18
 **/
public class WordUtils {
    public static void main(String[] args) throws IOException {
        test();
        System.out.println(22222);
    }
    private static void test() throws IOException {
        String templatePath ="C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\1立案登记表.docx";
        InputStream is =new FileInputStream(templatePath);
//        HWPFDocument doc =new HWPFDocument(is);
        HWPFDocument doc =new HWPFDocument(is);
        Range range = doc.getRange();
        //把range范围内的${reportDate}替换为当前的日期
        range.replaceText("${name1}", LocalDateTime.now().toString());
        range.replaceText("${name2}","100.00");
        range.replaceText("${name3}","200.00");
        range.replaceText("${name4}","300.00");
        range.replaceText("${sex}","男");
        range.replaceText("${age}","19");
        OutputStream os =new FileOutputStream("C:\\Users\\lastlySly\\Desktop\\write.docx");
        //把doc输出到输出流中
        doc.write(os);
        closeStream(os);
        closeStream(is);

    }
    /**
     * 关闭输入流
     * @param is
     */
    private static void closeStream(InputStream is) {
        if(is != null) {
            try{
                is.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * @param os
     */
    private static void closeStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

