package com.lastlysly.springbootapiversion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-07-16 09:07
 **/
@RestController
@RequestMapping("/{version}/version")
@ApiVersion(2)
public class V2Controller {
//    @GetMapping("{version}/test")
    @GetMapping
    public String v2Test(){
        return "第二版本";
    }
}
