package com.jcalvopinam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PocEhCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocEhCacheApplication.class, args);
    }

}
