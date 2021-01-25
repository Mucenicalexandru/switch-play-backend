package com.switchplaybackend.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class SwitchPlayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwitchPlayApplication.class, args);
        System.out.println("Application is running...");
    }

}
