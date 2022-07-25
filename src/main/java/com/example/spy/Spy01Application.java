package com.example.spy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.spy.mapper")
@SpringBootApplication
public class Spy01Application {

    public static void main(String[] args) {
        SpringApplication.run(Spy01Application.class, args);
    }

}
