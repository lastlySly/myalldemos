package com.lastlysly.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.lastlysly.view.UserInfo1;
import com.lastlysly.view.UserInfo2;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-21 17:06
 **/
@RestController
@RequestMapping("/easyPoi")
public class EasyPoiController {
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response){
        long start = System.currentTimeMillis();
        try {
            response.reset();
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            List<UserInfo2> list = getUsetList();
            int size = list.size();
            ExportParams params = new ExportParams("学生信息表","学生");
            Workbook workbook = ExcelExportUtil.exportExcel(params,
                    UserInfo2.class,list);
            response.setHeader("content-Type","application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
            System.out.println(list.size());
            System.out.println("easyPoi导出" + size +"条数据耗时:" + (System.currentTimeMillis() - start));
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Deprecated
    @RequestMapping("/exportBigExcel")
    public void exportBigExcel(HttpServletResponse response){
        long start = System.currentTimeMillis();
        try {
            response.reset();
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            List<UserInfo2> list = getUsetList();
            int size = list.size();
            ExportParams params = new ExportParams("学生信息表","学生");

            ExcelExportServerImpl excelExportServer = new ExcelExportServerImpl();
            Workbook workbook = ExcelExportUtil.exportBigExcel(params,
                    UserInfo2.class,excelExportServer,list);

            response.setHeader("content-Type","application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
            System.out.println(list.size());
            System.out.println("easyPoi导出" + size +"条数据耗时:" + (System.currentTimeMillis() - start));
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private List getUsetList() {
        List<UserInfo2> userList = new LinkedList<>();
        for (int i = 0; i< 150000; i ++) {
            UserInfo2 userInfo2 = new UserInfo2();
            userInfo2.setId("id" + i);
            userInfo2.setName("name" + i);
            userInfo2.setCreateDate(LocalDateTime.now());
            userInfo2.setAge(i);
            userList.add(userInfo2);
        }
        return userList;
    }
}
