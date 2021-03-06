package com.online.edu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableDiscoveryClient // nacos注册
@EnableFeignClients // 服务调用端
@ComponentScan(basePackages = {"com.online"})
public class EduApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(EduApplication.class, args);
        String ip = "127.0.0.1";
        String path = "";
        String port = "8888";
        System.out.println(
                ("\n----------------------------------------------------------\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + "/" + path + "swagger-ui.html\n" +
                "----------------------------------------------------------")
        );
    }
}
