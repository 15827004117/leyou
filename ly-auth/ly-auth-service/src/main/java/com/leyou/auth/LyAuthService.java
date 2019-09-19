package com.leyou.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 李静
 * @date 2019/9/18 14:05
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LyAuthService {
    public static void main(String[] args) {
        SpringApplication.run(LyAuthService.class, args);
    }
}
