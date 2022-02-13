package com.infinity.omos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OmosApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmosApplication.class, args);
	}

}
