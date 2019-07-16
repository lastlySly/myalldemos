package com.lastlysly.springbootapiversion;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-07-16 08:55
 **/
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

    /**
     * @return 版本号
     */
    int value() default 1;
}
