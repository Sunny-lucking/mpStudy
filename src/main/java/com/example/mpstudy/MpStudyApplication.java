package com.example.mpstudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.example.mpstudy.mapper")
public class MpStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpStudyApplication.class, args);
    }

}
