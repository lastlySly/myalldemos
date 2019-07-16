package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-02-13 17:24
 **/
@Configuration
@SpringBootApplication
@PropertySource(value = "classpath:wxpay/wxpay.properties")
public class PayDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayDemoApplication.class, args);
    }
}
