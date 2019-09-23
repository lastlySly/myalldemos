package com.lastlysly.getEnumItemList;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-23 11:29
 **/
public class EnumItemView {
    private Integer value;
    private String display;

    public EnumItemView(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    public EnumItemView() {
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
