package com.xrm.tickly.ticketing_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.xrm.tickly")
@EntityScan("com.xrm.tickly.model")
@EnableJpaRepositories("com.xrm.tickly.repository")
public class TicketingWebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketingWebAppApplication.class, args);
        System.out.println("Ticketing Application is running...");
    }
}