package com.lastlysly.entity;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-25 16:15
 **/
public class KeyValueItem {

    private String key;

    private String value;

    public KeyValueItem() {
    }

    public KeyValueItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
