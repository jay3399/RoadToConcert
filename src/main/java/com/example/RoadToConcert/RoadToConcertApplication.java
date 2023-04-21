package com.example.RoadToConcert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RoadToConcertApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoadToConcertApplication.class, args);
	}

}
