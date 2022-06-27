package com.mediscreen.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;


@SpringBootApplication
@EnableFeignClients ("com.mediscreen.central")
@EnableEurekaClient
@EnableWebFluxSecurity

public class CentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralApplication.class, args);
    }

}
