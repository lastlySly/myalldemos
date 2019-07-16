package com.lastlysly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-04-03 11:42
 **/
@Controller
public class HelloController {

    @RequestMapping("hello")
    @ResponseBody
    public String sayHello(){
        return "hello activiti";
    }


}
