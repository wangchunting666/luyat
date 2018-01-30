package com.luyat.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Herdsric-M-003 on 2018/1/26.
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.luyat.demo.dao")
public class DemoApplication {
    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class,args);
    }
}
