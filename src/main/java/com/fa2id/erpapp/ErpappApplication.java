package com.fa2id.erpapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fa2id.erpapp")
public class ErpappApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpappApplication.class, args);
    }
}
