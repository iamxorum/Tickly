package com.xrm.tickly.ticketing_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EntityScan("com.xrm.tickly.ticketing_app.model")
@EnableJpaRepositories("com.xrm.tickly.ticketing_app.repository")
@EnableAsync
public class TicketingWebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketingWebAppApplication.class, args);
    }
}