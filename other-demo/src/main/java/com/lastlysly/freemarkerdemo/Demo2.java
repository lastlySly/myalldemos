package com.lastlysly.freemarkerdemo;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.*;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-08-18 14:46
 **/
public class Demo2 {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\lastlySly\\Desktop\\test\\test23333.jpeg");
            FileOutputStream os = null;

            os = new FileOutputStream(file);
            InputStream in = new FileInputStream("C:\\Users\\lastlySly\\Desktop\\test\\23333.docx");


            Document doc2 = new Document(in);
//            Document doc = new Document("C:\\Users\\lastlySly\\Desktop\\testword.doc");
            ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
            iso.setResolution(200);
//            doc.save(os, iso);
            doc2.save(os, iso);
            // doc.save(os, SaveFormat.PNG);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Demo2.class.getClassLoader().getResourceAsStream("\\license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
