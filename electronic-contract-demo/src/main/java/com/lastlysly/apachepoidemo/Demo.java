package com.lastlysly.apachepoidemo;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-28 17:59
 **/
public class Demo {

    public static String doc2String2() {
        try {
            Demo wd = new Demo();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("name1", LocalDateTime.now().toString());
            params.put("name2","100.00");
            params.put("name3","200.00");
            params.put("name4","300.00");
            params.put("sex","男");
            params.put("age","19");
            String filePath = "C:\\Users\\lastlySly\\Desktop\\临时\\wordtemplate\\1立案登记表.docx";
            InputStream is = new FileInputStream(filePath);
            XWPFDocument doc = new XWPFDocument(is);
            //替换段落里面的变量
            wd.replaceInPara(doc, params);
            //替换表格里面的变量
            wd.replaceInTable(doc, params);
            OutputStream os = new FileOutputStream("C:\\Users\\lastlySly\\Desktop\\write.docx");
            doc.write(os);
            wd.close(os);
            wd.close(is);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }


    /**
     * 读取doc文件内容
     * @return 返回文件内容
     */
    public static String doc2String(){
        try {
            FileInputStream stream = new FileInputStream("d://1.docx");
            XWPFDocument doc = new XWPFDocument(stream);// 创建Word文件
            for(XWPFParagraph p : doc.getParagraphs())//遍历段落
            {
                System.out.println(p.getParagraphText());
            }

            for(XWPFTable table : doc.getTables())//遍历表格
            {
                for(XWPFTableRow row : table.getRows())
                {
                    for(XWPFTableCell cell : row.getTableCells())
                    {
                        System.out.println(cell.getText());
                    }
                }
            }
            FileOutputStream out = new FileOutputStream("d://sample.docx");
            doc.write(out);
            out.close();
            stream.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 替换段落里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }
    /**
     * 替换段落里面的变量
     * @param para 要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                System.out.println(runText);
                if (matcher.find()) {
                    System.out.println("进1");
                    while ((matcher = this.matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //doc2String();
        doc2String2();
    }

}