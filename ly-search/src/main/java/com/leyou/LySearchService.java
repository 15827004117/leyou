package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 李静
 * @date 2019/9/2 9:40
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LySearchService {

    public static void main(String[] args) {
        SpringApplication.run(LySearchService.class, args);
    }
}
