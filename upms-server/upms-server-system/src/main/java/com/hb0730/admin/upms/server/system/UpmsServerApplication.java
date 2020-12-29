package com.hb0730.admin.upms.server.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bing_huang
 */
@SpringBootApplication
@MapperScan("com.hb0730.admin.upms.**.mapper")
public class UpmsServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UpmsServerApplication.class, args);
    }
}
