package com.ghbt.ghbt_starbucks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GhbtStarbucksApplication {

	public static void main(String[] args) {
		SpringApplication.run(GhbtStarbucksApplication.class, args);
	}

}
