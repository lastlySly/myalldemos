package com.lastlysly.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import com.lastlysly.easyexcel.LocalDateTimeConver;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-21 15:53
 **/
@ColumnWidth(22)
@ContentRowHeight(15) //由于新版本并没有对单元格设置默认值
public class UserInfo1 extends BaseRowModel implements Serializable {
    @ExcelProperty(value = "id", index = 0)
    private String id;
    @ExcelProperty(value = "姓名", index = 1)
    private String name;
    @ExcelProperty(value = "时间", index = 2,converter = LocalDateTimeConver.class)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private LocalDateTime createDate;
    @ExcelProperty(value = "年龄", index = 3)
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

    @Override
    protected void finalize() throws Throwable {
        System.out.println("垃圾回收userinfo1");
    }
}
