package com.leyou.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 李静
 * @date 2019/9/19 16:39
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LyCartService {
    public static void main(String[] args) {
        SpringApplication.run(LyCartService.class, args);
    }
}
