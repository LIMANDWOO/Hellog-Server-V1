package com.hellog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HellogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellogApplication.class, args);
	}

}
