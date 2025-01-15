package com.devsuperior.hroauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HrOuathApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrOuathApplication.class, args);
	}

}