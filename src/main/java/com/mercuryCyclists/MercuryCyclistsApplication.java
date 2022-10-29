package com.mercuryCyclists;

import com.mercuryCyclists.businessIntelligence.service.SaleStreamGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
		Long[] productIdArray = new Long[3];
		productIdArray[0] = saleStreamGenerator.createTestProduct("PushBike", 1000L, "Push Bike", 500L);
		productIdArray[1] = saleStreamGenerator.createTestProduct("MountainBike", 1500L, "Mountain Bike", 750L);
		productIdArray[2] = saleStreamGenerator.createTestProduct("NormalBike", 800L, "Normal Bike", 600L);

		while(true){
			Thread.sleep(2000);
			int productIdArrayIndex = 0 + (int)(Math.random() * ((2 - 0) + 1)); // Random int [0, 2]
			Long ranQuantity = Math.round((double)(1 + (int)(Math.random() * ((3 - 1) + 1)))); // Random Long [1, 3]
			saleStreamGenerator.createTestBackorder("Caitlyn", "Wollongong", productIdArray[productIdArrayIndex], ranQuantity);
		}
	}
}
