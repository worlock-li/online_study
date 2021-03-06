package com.online.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

// springBoot的启动注解，exclude排除数据库加载的相关配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient // nacos注册
@ComponentScan(basePackages = {"com.online"})
public class VodApplication {
	public static void main(String[] args) {
		SpringApplication.run(VodApplication.class, args);
	}
}
