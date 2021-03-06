package com.lastlysly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ElectronicContractDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicContractDemoApplication.class, args);
    }

}
