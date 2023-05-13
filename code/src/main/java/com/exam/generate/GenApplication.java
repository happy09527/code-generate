package com.exam.generate;

import com.exam.generate.builder.BuilderTable;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: ZhangX
 * @createDate: 2023/5/13
 * @description:
 */

//@SpringBootApplication
//@MapperScan(basePackages = "com.exam.generate.mapper")
public class GenApplication {
    public static void main(String[] args) {
//        SpringApplication.run(GenApplication.class,args);
        BuilderTable.getTables();
    }
}
