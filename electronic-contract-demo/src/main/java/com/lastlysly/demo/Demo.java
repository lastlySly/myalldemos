package com.lastlysly.demo;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;
import com.google.common.collect.Maps;
import com.lastlysly.utils.CustomAsposeWordsUtils;
import freemarker.core.ParseException;
import freemarker.template.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-05 10:03
 **/
public class Demo {

    public static void main(String[] args) {
        Map map = CustomAsposeWordsUtils.getDataMap();
//        生成电子合同到指定位置   文档格式
//        CustomAsposeWordsUtils.createDocToPath();

//        生成电子合同到指定位置   转pdf格式
        CustomAsposeWordsUtils.createPdfToPath("C:/Users/lastlySly/Desktop/1.pdf",map);


//        生成电子合同到指定位置   转图片格式
//        CustomAsposeWordsUtils.createImageToPath(map);
    }


}
