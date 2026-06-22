package com.qingyunzong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qingyunzong.mapper")
public class TreasurePavilionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreasurePavilionApplication.class, args);
    }
}
