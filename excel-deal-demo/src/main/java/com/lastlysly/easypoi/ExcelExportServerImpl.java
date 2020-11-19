package com.lastlysly.easypoi;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.lastlysly.view.UserInfo2;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-27 09:52
 **/
public class ExcelExportServerImpl implements IExcelExportServer {
    @Override
    public List<Object> selectListForExcelExport(Object queryParams, int page) {

        return getUsetList();
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
