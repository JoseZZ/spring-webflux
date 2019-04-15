package com.webflux.client;

import com.webflux.bussiness.bean.Employee;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EmployeeWebClient {

    // WebClient es un cliente no bloqueante con soporte para reactive streams
    WebClient client = WebClient.create("http://localhost:8090");
    
    public void consume() {

        Mono<Employee> employeeMono = client.get()
            .uri("/employees/{id}", "10001")
            .retrieve()
            .bodyToMono(Employee.class);

        employeeMono.subscribe(System.out::println);
        
        Flux<Employee> employeeFlux = client.get()
            .uri("/employees")
            .retrieve()
            .bodyToFlux(Employee.class);
        
        employeeFlux.subscribe(System.out::println);
    }
}