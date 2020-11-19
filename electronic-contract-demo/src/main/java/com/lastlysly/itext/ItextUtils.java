package com.lastlysly.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-29 10:18
 **/
public class ItextUtils {
    private static final Logger logger = LoggerFactory.getLogger(ItextUtils.class);
    /**
     *
     * 【功能描述：添加图片水印】 【功能详细描述：功能详细描述】
     * @param srcFileBytes 待加水印文件
     * @param outputStream 加水印后 输出流
     * @param imageBytes 水印图片路径
     * @param waterMarkXPositon 水印横坐标
     * @param waterMarkYPositon 水印纵坐标
     * @param scale 水印缩放
     * @throws Exception
     */
    public static void addImageWaterMark(byte[] srcFileBytes, ByteArrayOutputStream outputStream, byte[] imageBytes,
                                         int waterMarkXPositon, int waterMarkYPositon,int scale) throws Exception {
        // 待加水印的文件
        PdfReader reader = new PdfReader(srcFileBytes);
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, outputStream);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        // 循环对每页插入水印
        for (int i = 1; i < total; i++) {
            // 水印的起始
            content = stamper.getUnderContent(i);
            // 开始
            content.beginText();
            Image image = Image.getInstance(imageBytes);
            // 设置坐标 绝对位置 X Y
            image.setAbsolutePosition(waterMarkXPositon, waterMarkYPositon);
            // 设置旋转弧度,旋转 弧度
//            image.setRotation(30);
            // 设置旋转角度,旋转 角度
//            image.setRotationDegrees(45);
            // 设置等比缩放,依照比例缩放
            image.scalePercent(scale);
            content.addImage(image);

            content.endText();
        }
        stamper.close();
    }
    /**
     *
     * 【功能描述：添加图片水印】 【功能详细描述：功能详细描述】
     * @param srcFile 待加水印文件
     * @param destFile 加水印后存放地址
     * @param imagePath 水印图片路径
     * @param waterMarkXPositon 水印横坐标
     * @param waterMarkYPositon 水印纵坐标
     * @param scale 水印缩放
     * @throws Exception
     */
    public static void addImageWaterMark(String srcFile, String destFile, String imagePath,
                             int waterMarkXPositon, int waterMarkYPositon,int scale) throws Exception {
        // 待加水印的文件
        PdfReader reader = new PdfReader(srcFile);
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destFile));
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        // 循环对每页插入水印
        for (int i = 1; i < total; i++) {
            // 水印的起始
            content = stamper.getUnderContent(i);
            // 开始
            content.beginText();
            Image image = Image.getInstance(imagePath);
            // 设置坐标 绝对位置 X Y
            image.setAbsolutePosition(waterMarkXPositon, waterMarkYPositon);
            // 设置旋转弧度,旋转 弧度
//            image.setRotation(30);
            // 设置旋转角度,旋转 角度
//            image.setRotationDegrees(45);
            // 设置等比缩放,依照比例缩放
            image.scalePercent(scale);
            content.addImage(image);

            content.endText();
        }
        stamper.close();
    }

    /**
     *
     * 【功能描述：添加文字水印】 【功能详细描述：功能详细描述】
     * @param srcFile 待加水印文件
     * @param destFile 加水印后存放地址
     * @param text 加水印的文本内容
     * @param textWidth 文字横坐标
     * @param textHeight 文字纵坐标
     * @throws Exception
     */
    public void addTextWaterMark(String srcFile, String destFile, String text,
                             int textWidth, int textHeight) throws Exception {
        // 待加水印的文件
        PdfReader reader = new PdfReader(srcFile);
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                destFile));
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        // 设置字体
        BaseFont font = BaseFont.createFont();
        // 循环对每页插入水印
        for (int i = 1; i < total; i++)
        {
            // 水印的起始
            content = stamper.getUnderContent(i);
            // 开始
            content.beginText();
            // 设置颜色 默认为蓝色
            content.setColorFill(BaseColor.BLUE);
            // content.setColorFill(Color.GRAY);
            // 设置字体及字号
            content.setFontAndSize(font, 38);
            // 设置起始位置
            // content.setTextMatrix(400, 880);
            content.setTextMatrix(textWidth, textHeight);
            // 开始写入水印
            content.showTextAligned(Element.ALIGN_LEFT, text, textWidth,
                    textHeight, 45);
            content.endText();
        }
        stamper.close();
    }


    /**
     * 利用模板生成pdf
     * @param data 写入的数据
     * @param out 自定义保存pdf的文件流
     * @param templateBytes pdf模板字节数组
     *                     利用模板生成pdf
     */
    public static void fillTemplate(Map<String, Object> data, OutputStream out, byte[] templateBytes) throws IOException, DocumentException {
        // 读取pdf模板
        PdfReader reader = new PdfReader(templateBytes);
        fillPdfToTemplate(data, out, reader);
    }

    /**
     * 利用模板生成pdf
     * @param data 写入的数据
     * @param out 自定义保存pdf的文件流
     * @param templatePath pdf模板路径
     *
     */
    public static void fillTemplate(Map<String, Object> data, OutputStream out, String templatePath) throws IOException, DocumentException {
        // 读取pdf模板
        PdfReader reader = new PdfReader(templatePath);
        fillPdfToTemplate(data, out, reader);
    }

    /**
     * 利用模板生成pdf
     * @param data
     * @param out
     * @param reader
     * @throws DocumentException
     * @throws IOException
     */
    private static void fillPdfToTemplate(Map<String, Object> data, OutputStream out, PdfReader reader) throws DocumentException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, bos);
        AcroFields form = stamper.getAcroFields();

        Iterator<String> it = form.getFields().keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            String value = data.get(name) != null ? data.get(name).toString() : "";
            logger.info("渲染pdf模板表单中[{}]字段，替换为值[{}],开始渲染", name, value);
            form.setField(name, value);
            logger.info("渲染pdf模板表单中[{}]字段，替换为值[{}],渲染成功");
        }
        // 如果为false那么生成的PDF文件还能编辑，一定要设为true
        stamper.setFormFlattening(true);
        stamper.close();

        Document doc = new Document();
        PdfCopy copy = new PdfCopy(doc, out);
        doc.open();
        PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
        copy.addPage(importPage);
        doc.close();
    }
}

