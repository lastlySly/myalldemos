package com.lastlysly.getEnumItemList;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-23 11:34
 **/
public enum  MyTestEnum implements IIntegerEnum {

    test_1(1,"测试1"),
    test_2(2,"测试2"),
    test_3(3,"测试3");

    private Integer value;
    private String display;

    MyTestEnum(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    /**
     * 根据value获取display
     *
     * @param value : 键值key
     * @return String
     */
    public static String getDisplayByValue(String value) {
        MyTestEnum[] enums = MyTestEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getValue().equals(value)) {
                return enums[i].getDisplay();
            }
        }
        return "";
    }


    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDisplay() {
        return display;
    }
}
