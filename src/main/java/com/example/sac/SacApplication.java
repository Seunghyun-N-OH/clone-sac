package com.example.sac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SacApplication {

	public static void main(String[] args) {
		SpringApplication.run(SacApplication.class, args);
	}

}
