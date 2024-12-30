package com.xrm.tickly.ticketing_app_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.xrm.tickly")
@EntityScan("com.xrm.tickly.ticketing_app_test.model")
@EnableJpaRepositories("com.xrm.tickly.ticketing_app_test.repository")
public class TestTicketingWebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.xrm.tickly.ticketing_app_test.TestTicketingWebAppApplication.class, args);
        System.out.println("Ticketing Application is running...");
    }
}