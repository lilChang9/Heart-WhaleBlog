package com.heart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.heart.mapper")
public class FrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class,args);
    }
}
