package com.lastlysly.getEnumItemList;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-23 11:42
 **/
@Controller
@RequestMapping("/demo")
public class DemoController {

    /**
     * 返回枚举列表，枚举需要实现IIntegerEnum接口。并定义在统一的包下
     * @param enumName
     * @return
     * @throws ClassNotFoundException
     */
    @GetMapping("enum/{enumName}")
    @ResponseBody
    public List<EnumItemView> getEnum(@PathVariable String enumName) throws ClassNotFoundException {
//        MyTestEnum
        String className = "com.lastlysly.getEnumItemList." + enumName;
        if (!className.endsWith("Enum")){
            className += "Enum";
        }
        Class<IIntegerEnum> enumClass = (Class<IIntegerEnum>) Class.forName(className);
        IIntegerEnum[] enums = enumClass.getEnumConstants();
        List<EnumItemView> enumItemViewList = Lists.newArrayList();
        for (IIntegerEnum anEnum : enums){
            EnumItemView enumItemView = new EnumItemView();
            enumItemView.setValue(anEnum.getValue());
            enumItemView.setDisplay(anEnum.getDisplay());
            enumItemViewList.add(enumItemView);
        }
        return enumItemViewList;
    }
}
