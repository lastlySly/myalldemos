package com.lastlysly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-25 11:36
 **/
@SpringBootApplication
public class TcpDemoApplication {
    public static void main(String[] args) {
//		SpringApplication.run(LivechatApplication.class, args);
        SpringApplication springApplication = new SpringApplication(TcpDemoApplication.class);
//        		springApplication.setBannerMode(Banner.Mode.OFF); 关闭banner
        springApplication.run(args);
    }

}
