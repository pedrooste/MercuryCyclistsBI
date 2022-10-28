package com.mercuryCyclists;

import com.mercuryCyclists.businessIntelligence.service.SaleStreamGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application
 */
@SpringBootApplication
public class MercuryCyclistsApplication implements CommandLineRunner {

	@Autowired
	SaleStreamGenerator saleStreamGenerator;

	public static void main(String[] args) {
		SpringApplication.run(MercuryCyclistsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Long productId = saleStreamGenerator.createTestProduct("Bike", 1000L, "Pushbike", 500L);

		while(true){
			Thread.sleep(1000);
			saleStreamGenerator.createTestBackorder("Caitlyn", "Wollongong", productId, 1L);
		}
	}

}
