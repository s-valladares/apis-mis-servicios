package com.example.apismisservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ApisMisServiciosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApisMisServiciosApplication.class, args);
    }

}
