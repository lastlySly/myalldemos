package com.lastlysly.easyexcel;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.google.common.collect.Lists;
import com.lastlysly.view.UserInfo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-21 16:08
 **/
@RestController
@RequestMapping("/easyExcel")
public class EasyExcelController {
    private ExecutorService executorService = new ThreadPoolExecutor(5,
            10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r,"线程");
                    return thread;
                }
            });
    /**
     * 官方
     * @param response
     * @throws IOException
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<UserInfo1> datas = getUsetList();
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UserInfo1.class).sheet("模板").doWrite(datas);
        datas.clear();
        System.out.println("easyExcel导出" + datas.size()+"条数据耗时:" + (System.currentTimeMillis() - start));
    }

    /**
     * localhost:8080/easyExcel/downloadPage
     * @param response
     * @throws IOException
     */
    @GetMapping("downloadPage")
    public void downloadPage(HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<UserInfo1> datas = getUsetList();
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        OutputStream out = response.getOutputStream();

        WriteWorkbook writeWorkbook = new WriteWorkbook();
        writeWorkbook.setOutputStream(out);
        writeWorkbook.setExcelType(ExcelTypeEnum.XLSX);
        writeWorkbook.setNeedHead(true);
        ExcelWriter writer = new ExcelWriter(writeWorkbook);
        List<List<UserInfo1>> datasList = Lists.partition(datas,50000);
        // 一页五万条，自动分页
        for (int i = 0; i < datasList.size(); i++) {
            WriteSheet sheet = new WriteSheet();
            sheet.setSheetName("sheet" + i);
            sheet.setSheetNo(i + 1);
            writer.write(datasList.get(i),sheet);
        }
        datas.clear();
        writer.finish();
        System.out.println("easyExcel导出" + datas.size()+"条数据耗时:" + (System.currentTimeMillis() - start));
    }

    /**
     * localhost:8080/easyExcel/downloadDataPage
     * @param response
     * @throws IOException
     */
    @GetMapping("downloadDataPage")
    public void downloadDataPage(HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<UserInfo1> datas = getUsetList();
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        OutputStream out = response.getOutputStream();

        WriteWorkbook writeWorkbook = new WriteWorkbook();
        writeWorkbook.setOutputStream(out);
        writeWorkbook.setExcelType(ExcelTypeEnum.XLSX);
        writeWorkbook.setNeedHead(true);
        ExcelWriter writer = new ExcelWriter(writeWorkbook);
        List<List<UserInfo1>> datasList = Lists.partition(datas,50000);
        WriteSheet sheet = new WriteSheet();
        sheet.setSheetName("sheet");
        sheet.setSheetNo(1);
        // 一页五万条，自动分页
        for (int i = 0; i < datasList.size(); i++) {
            writer.write(datasList.get(i),sheet);
        }
        datas.clear();
        writer.finish();
        System.out.println("easyExcel导出" + datas.size()+"条数据耗时:" + (System.currentTimeMillis() - start));
    }


    /**
     * localhost:8080/easyExcel/downloadZip
     * @param response
     * @throws IOException
     */
    @GetMapping("downloadZip")
    public void downloadZip(HttpServletResponse response) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        List<UserInfo1> datas = getUsetList();
        String fileName = URLEncoder.encode(LocalDateTime.now().toString(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".zip");
        OutputStream out = response.getOutputStream();


        List<List<UserInfo1>> datasList = Lists.partition(datas,1000);

        ZipOutputStream zos = null;
        zos = new ZipOutputStream(response.getOutputStream());

        for (int i = 0; i < datasList.size(); i++) {
            List datasListItem = datasList.get(i);
            ByteArrayOutputStream os =new ByteArrayOutputStream();
            EasyExcel.write(os, UserInfo1.class)
                    .sheet("sheet1").doWrite(datasListItem);
            InputStream is =new ByteArrayInputStream(os.toByteArray());
            ZipEntry zipEntry = new ZipEntry(i + ".xlsx");
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
            zos.closeEntry();
            /**
             * 关闭输入流
             */
            is.close() ;
        }
        datas.clear();
        zos.flush();
        zos.close();
        System.out.println("easyExcel多线程导出" + datas.size()+"条数据耗时:" + (System.currentTimeMillis() - start));
    }


    /**
     * localhost:8080/easyExcel/downloadThreadZip
     * @param response
     * @throws IOException
     */
    @GetMapping("downloadThreadZip")
    public void downloadThreadZip(HttpServletResponse response) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(LocalDateTime.now().toString(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".zip");
        List<UserInfo1> datas = getUsetList();
        List<List<UserInfo1>> datasList = Lists.partition(datas,10000);

        ZipOutputStream zos = null;
        zos = new ZipOutputStream(response.getOutputStream());
        AtomicInteger count = new AtomicInteger(0);
        List<Future<ByteArrayOutputStream>> futureList = Lists.newArrayList();
        for (int i = 0; i < datasList.size(); i++) {
            List datasListItem = datasList.get(i);
            Future<ByteArrayOutputStream> future = executorService.submit(new Callable<ByteArrayOutputStream>() {
                @Override
                public ByteArrayOutputStream call() throws Exception {

                    System.out.println(23333);
                    ByteArrayOutputStream os =new ByteArrayOutputStream();
                    EasyExcel.write(os, UserInfo1.class)
                            .sheet("sheet1").doWrite(datasListItem);
//                        InputStream is = new ByteArrayInputStream(os.toByteArray());
//                        File file = new File("C:\\Users\\lastlySly\\Desktop\\" + count.incrementAndGet() + ".xlsx");
//                        FileOutputStream in = new FileOutputStream(file);
//                        in.write(os.toByteArray());
//
//
//                        in.close();
                    return os;
                }
            });
            futureList.add(future);
        }
        int cc = 0;
        for (Future<ByteArrayOutputStream> future : futureList) {
            try {
                if (future.get() != null) {
//                    InputStream is = new ByteArrayInputStream(future.get().toByteArray());
//                    File file = new File("C:\\Users\\lastlySly\\Desktop\\" + cc + ".xlsx");
//                    FileOutputStream in = new FileOutputStream(file);
//                    in.write(future.get().toByteArray());
//                    in.close();
                    ByteArrayOutputStream os = future.get();
                    InputStream is =new ByteArrayInputStream(os.toByteArray());
                    ZipEntry zipEntry = new ZipEntry(cc + ".xlsx");
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
                    zos.closeEntry();
                    /**
                     * 关闭输入流
                     */
                    is.close();

                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            cc++;
        }

        System.out.println(111111);
        datas.clear();
        zos.flush();
        zos.close();
        System.out.println("easyExcel多线程导出" + datas.size()+"条数据耗时:" + (System.currentTimeMillis() - start));
    }


    /**
     * localhost:8080/easyExcel/downloadThreadZip
     * @param response
     * @throws IOException
     */
    @GetMapping("downloadThreadPage")
    public void downloadThreadPage(HttpServletResponse response) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(LocalDateTime.now().toString(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".zip");
        List<UserInfo1> datas = getUsetList();
        List<List<UserInfo1>> datasList = Lists.partition(datas,10000);

        ZipOutputStream zos = null;
        zos = new ZipOutputStream(response.getOutputStream());
        AtomicInteger count = new AtomicInteger(0);
        List<Future<ByteArrayOutputStream>> futureList = Lists.newArrayList();
        for (int i = 0; i < datasList.size(); i++) {
            List datasListItem = datasList.get(i);
            Future<ByteArrayOutputStream> future = executorService.submit(new Callable<ByteArrayOutputStream>() {
                @Override
                public ByteArrayOutputStream call() throws Exception {

                    System.out.println(23333);
                    ByteArrayOutputStream os =new ByteArrayOutputStream();
                    EasyExcel.write(os, UserInfo1.class)
                            .sheet("sheet1").doWrite(datasListItem);
//                        InputStream is = new ByteArrayInputStream(os.toByteArray());
//                        File file = new File("C:\\Users\\lastlySly\\Desktop\\" + count.incrementAndGet() + ".xlsx");
//                        FileOutputStream in = new FileOutputStream(file);
//                        in.write(os.toByteArray());
//
//
//                        in.close();
                    return os;
                }
            });
            futureList.add(future);
        }
        int cc = 0;
        for (Future<ByteArrayOutputStream> future : futureList) {
            try {
                if (future.get() != null) {
//                    InputStream is = new ByteArrayInputStream(future.get().toByteArray());
//                    File file = new File("C:\\Users\\lastlySly\\Desktop\\" + cc + ".xlsx");
//                    FileOutputStream in = new FileOutputStream(file);
//                    in.write(future.get().toByteArray());
//                    in.close();
                    ByteArrayOutputStream os = future.get();
                    InputStream is =new ByteArrayInputStream(os.toByteArray());
                    ZipEntry zipEntry = new ZipEntry(cc + ".xlsx");
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
                    zos.closeEntry();
                    /**
                     * 关闭输入流
                     */
                    is.close();

                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            cc++;
        }

        System.out.println(111111);
        datas.clear();
        zos.flush();
        zos.close();
        System.out.println("easyExcel多线程导出" + datas.size()+"条数据耗时:" + (System.currentTimeMillis() - start));
    }


    private List getUsetList() {
        List<UserInfo1> userList = new LinkedList<>();
        for (int i = 0; i< 150000; i ++) {
            UserInfo1 userInfo1 = new UserInfo1();
            userInfo1.setId("id" + i);
            userInfo1.setName("name" + i);
            userInfo1.setCreateDate(LocalDateTime.now());
            userInfo1.setAge(i);
            userList.add(userInfo1);
        }
        return userList;
    }
}
