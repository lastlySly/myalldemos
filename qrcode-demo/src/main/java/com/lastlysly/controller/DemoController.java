package com.lastlysly.controller;

import com.google.zxing.WriterException;
import com.lastlysly.MyQrCode.MyQrCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-04-15 13:56
 **/
@Controller
public class DemoController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 设备二维码批量下载
     * @param ids  设备ID
     * @param qrcodeByType 根据（imei，终端号）生成二维码
     * @param response
     * @param request
     */
    @RequestMapping(value = "qrcodeDownLoad")
    @ResponseBody
    public void qrcodeDownLoad(String ids, String qrcodeByType, HttpServletResponse response, HttpServletRequest request){

        ids = "1001,1002,1003,1004";
        qrcodeByType = "测试";
        String idArray[] =ids.split(",");
        logger.info("将生成这些以{}为标识的二维码{}",qrcodeByType,ids);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=qrcode.zip");
        ZipOutputStream zos = null;
        try {
            /**
             * 文件压缩输出流
             */
            zos = new ZipOutputStream(response.getOutputStream());
            for(String id : idArray){

                BufferedImage img = null;
//                String logoPath = request.getServletContext().getRealPath("static/qrlogo.png");
                String logoPath = "F:/MyDevelopmentData/test/qrlogo.png";
//                String templatePath = request.getServletContext().getRealPath("static/qr_template.png");
                String templatePath = "F:/MyDevelopmentData/test/qr_template.png";
//                String placeAnOrderUrl = "下订单接口?createSign=" + createSign + "&qrcodeByType=" + qrcodeByType;
                /**
                 * 生成二维码
                 */
                img = MyQrCodeUtils.createQR("设备id为：" + id +  "。标识类型：" + qrcodeByType, 400, 400, logoPath);
                /**
                 * 追加二维码模板
                 */
                img = MyQrCodeUtils.addWaterImage(templatePath, img, 180, 220);
                /**
                 * 追加文字
                 */
                img = MyQrCodeUtils.addWaterText(id, img, "微软雅黑", 0, Color.BLACK, 30, 230, 620);

                /**
                 * 这里需要使用png或者gif。   水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
                 */
                /*ImageIO.write(img, "png", new File("C:/Users/lastlySly/Desktop/二维码/"+ id +".png"));*/

                /**
                 * BufferedImage 转 流 start
                 */
                ByteArrayOutputStream os =new ByteArrayOutputStream();
                ImageIO.write(img,"png",os);
                InputStream is =new ByteArrayInputStream(os.toByteArray());
                /**
                 * BufferedImage 转 流 end
                 */

                ZipEntry zipEntry = new ZipEntry(id + ".png");
                zos.putNextEntry(zipEntry);
                zos.setComment("测试多文件压缩下载");
                int temp = 0 ;
                /**
                 * 读取内容
                 */
                while((temp=is.read())!=-1){
                    /**
                     *  压缩输出
                     */
                    zos.write(temp);
                }
                /**
                 * 关闭输入流
                 */
                is.close() ;
            }
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            /**
             * 关闭流
             */
            try {
                if (null != zos){
                    zos.flush();
                    zos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
