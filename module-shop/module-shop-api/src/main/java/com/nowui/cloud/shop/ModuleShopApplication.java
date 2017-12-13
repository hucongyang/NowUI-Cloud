package com.nowui.cloud.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ZhongYongQiang
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={"com.nowui.cloud"})
public class ModuleShopApplication{
    public static void main(String[] args) {
        SpringApplication.run(ModuleShopApplication.class, args);
    }
}
