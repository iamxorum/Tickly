package com.xrm.tickly.ticketing_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketingWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingWebAppApplication.class, args);
		System.out.println("Ticketing Application is running...");
	}

}
