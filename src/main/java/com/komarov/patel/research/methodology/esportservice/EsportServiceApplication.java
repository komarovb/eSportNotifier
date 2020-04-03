package com.komarov.patel.research.methodology.esportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EsportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsportServiceApplication.class, args);
	}

}
