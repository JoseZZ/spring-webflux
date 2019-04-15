package com.webflux;

import com.webflux.client.EmployeeWebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationBootUp {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationBootUp.class);

		// Hacemos una prueba con el cliente
		EmployeeWebClient employeeWebClient = new EmployeeWebClient();
		employeeWebClient.consume();
	}

}
