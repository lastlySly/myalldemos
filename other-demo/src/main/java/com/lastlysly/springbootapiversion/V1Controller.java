package com.lastlysly.springbootapiversion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-07-16 09:00
 **/
@RestController
@RequestMapping("/{version}/version")
@ApiVersion(1)
public class V1Controller {

//    @GetMapping("{version}/test")
    @GetMapping("test")
    public String v1Test(){
        return "第一版本";
    }

    @GetMapping("{version}/demo")
    public String v1Demo(@PathVariable("version") String version){
        return "第一版本demo";
    }

}
