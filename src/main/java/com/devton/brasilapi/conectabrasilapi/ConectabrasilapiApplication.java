package com.devton.brasilapi.conectabrasilapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConectabrasilapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConectabrasilapiApplication.class, args);
	}

}
