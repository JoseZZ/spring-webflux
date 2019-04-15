package com.webflux.web.router.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.web.handler.EmployeeHandler;


@Configuration

/**
 * Router is configuration class.
 * It links the incoming requests with appropriate HTTP methods to the 
 * respective method of the EmployeeHandler.
 * Method references are used for the mapping.
 * */
public class RouterConfiguration{
    @Autowired
    EmployeeHandler employeeHandler;

    // En Spring WebFlux las peticiones ÇHTTP se enrutan via funciones router
    // Es la alternativa a la anotacion @RequestMapping
    
    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction() {
    	
     RouterFunction<ServerResponse>routerFunction=  RouterFunctions.		
              route(GET("/emp/controller/getDetails").
              and(accept(MediaType.APPLICATION_JSON)),          
              employeeHandler::getEmployeeDetails)
    			
            .andRoute(GET("/emp/controller/getDetailsById/{id}")
            .and(accept(MediaType.APPLICATION_JSON)),            
             employeeHandler::getEmployeeDetailByEmployeeId)
    
            .andRoute(POST("/emp/controller/addEmp")
            .and(accept(MediaType.APPLICATION_JSON)), 
            employeeHandler::addEmployee)
    					
            .andRoute(PUT("/emp/controller/updateEmp")
            .and(accept(MediaType.APPLICATION_JSON)), 
            employeeHandler::updateEmployee)
    					
            .andRoute(DELETE("/emp/controller/deleteEmp/{id}")
            .and(accept(MediaType.APPLICATION_JSON)), 
            employeeHandler::deleteEmployee);

        return routerFunction;
    } 
  
}
