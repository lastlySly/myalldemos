package com.lastlysly.springbootapiversion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-07-16 09:18
 **/
@RestController
@RequestMapping("he")
public class HelloController {

    @GetMapping("hello")
    public String sayHello(){
        return "hello";
    }
}
