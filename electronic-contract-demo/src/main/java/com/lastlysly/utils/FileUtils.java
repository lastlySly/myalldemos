package com.lastlysly.utils;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-29 12:03
 **/
public class FileUtils {
    /**
     * 文件转为二进制数组
     * @param file
     * @return
     */
    public static byte[] fileToBinArray(File file){
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            return bytes;
        }catch (Exception ex){
            throw new RuntimeException("transform file into bin Array 出错",ex);
        }
    }
}
