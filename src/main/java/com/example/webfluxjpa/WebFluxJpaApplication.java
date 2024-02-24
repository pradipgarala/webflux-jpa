package com.example.webfluxjpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class WebFluxJpaApplication implements CommandLineRunner {


    public static void main(String[] args) {
		SpringApplication.run(WebFluxJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
