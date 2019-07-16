package com.lastlysly.common;

public enum MyCustomEnum {

    MY_CUSTOM_ENUM_0("0","转为16进制后传输"),
    MY_CUSTOM_ENUM_1("1","getBytes(\"US-ASCII\")后传输");

    private String name;
    private String index;
    // 构造方法
    private MyCustomEnum(String index, String name) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(String index) {
        for (MyCustomEnum c : MyCustomEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }



}
