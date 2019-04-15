package com.webflux.web.controller;

import com.webflux.bussiness.bean.Employee;
import com.webflux.dao.EmployeeDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/currantes")
public class EmployeeController {

    private EmployeeDAO employeeDAO;

    public EmployeeController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable String id) {
        return employeeDAO.getEmployeeDetailsById(Integer.parseInt(id));
    }

    @GetMapping
    private Flux<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployee();
    }

    @PostMapping
    private Mono<Employee> updateEmployee(@RequestBody Employee employee) {
        return employeeDAO.updateEmployee(employee);
    }

}
