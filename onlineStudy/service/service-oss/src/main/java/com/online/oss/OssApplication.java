package com.online.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

// exclude = DataSourceAutoConfiguration.class 不加载数据库相关的类
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.online"})
public class OssApplication {
	public static void main(String[] args) {
		SpringApplication.run(OssApplication.class, args);
	}
}
