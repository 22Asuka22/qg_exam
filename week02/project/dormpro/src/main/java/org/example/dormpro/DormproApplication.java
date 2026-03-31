package org.example.dormpro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.zjy.mapper")
@ComponentScan(basePackages = "com.zjy")
public class DormproApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormproApplication.class, args);
    }

}
