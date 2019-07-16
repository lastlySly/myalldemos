package com.lastlysly.entity;

import cn.hutool.core.util.ObjectUtil;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-25 17:37
 **/
public class MyResult {

    private int code;
    private String tip;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MyResult() {
    }

    public MyResult(int code, String tip, Object data) {
        this.code = code;
        this.tip = tip;
        this.data = data;
    }
}
