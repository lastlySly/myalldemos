package com.lastlysly.view;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.time.LocalDateTime;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-21 15:53
 **/
public class UserInfo2 {
    @Excel(name = "id",width = 30,orderNum = "1")
    private String id;
    @Excel(name = "姓名",width = 30,orderNum = "2")
    private String name;
    @Excel(name = "时间",width = 30,orderNum = "3",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @Excel(name = "年龄",width = 30,orderNum = "4")
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
//    @Override
//    protected void finalize() throws Throwable {
//        System.out.println("垃圾回收userinfo2");
//    }
}
