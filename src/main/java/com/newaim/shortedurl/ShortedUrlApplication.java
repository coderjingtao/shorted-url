package com.newaim.shortedurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Joseph.Liu
 */
@EnableAsync
@SpringBootApplication
public class ShortedUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortedUrlApplication.class, args);
    }

}
