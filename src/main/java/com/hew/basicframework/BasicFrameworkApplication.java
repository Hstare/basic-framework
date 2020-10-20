package com.hew.basicframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BasicFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicFrameworkApplication.class, args);
    }

}
