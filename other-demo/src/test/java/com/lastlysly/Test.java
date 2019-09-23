package com.lastlysly;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-03 16:05
 **/
public class Test {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>(16);
        String val = "";
        map.put("test",val == null ? "true" : "false");

        System.out.println(map.get("test"));
    }
}
