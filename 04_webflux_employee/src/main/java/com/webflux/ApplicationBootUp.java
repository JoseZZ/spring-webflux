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

	/**
	 * Spring WebFlux supports two types of programming models :
	 *
	 * Traditional annotation-based model with @Controller, @RequestMapping, and other annotations that you have been using in Spring MVC.
	 *
	 * A brand new Functional style model based on Java 8 lambdas for routing and handling requests.
	 */

}
