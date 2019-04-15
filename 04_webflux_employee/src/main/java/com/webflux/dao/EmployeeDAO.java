package com.webflux.dao;

import com.webflux.bussiness.bean.Employee;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class EmployeeDAO {


    /**
     * Map is used to Replace the Database
     */
    static public Map<Integer, Employee> mapOfEmloyeess = new LinkedHashMap<Integer, Employee>();
    static int count = 10004;

    static {
        mapOfEmloyeess.put(10001, new Employee("Jack", 10001, 12345.6, 1001));
        mapOfEmloyeess.put(10002, new Employee("Justin", 10002, 12355.6, 1002));
        mapOfEmloyeess.put(10003, new Employee("Eric", 10003, 12445.6, 1003));
    }

    /**
     * Used to return all the Existing Employees as Flux of Employee
     */
    public Flux<Employee> getAllEmployee() {
        return Flux.fromStream(mapOfEmloyeess.values().stream());
    }

    /**
     * Used to Find an Employee. Return a Mono<Employee> response with Data if Employee is Found Else return a null
     */
    public Mono<Employee> getEmployeeDetailsById(int id) {

        Mono<Employee> res = null;
        Employee emp = mapOfEmloyeess.get(id);
        if (emp != null) {
            res = Mono.just(emp);
        }
        return res;
    }


    /**
     * Used to save/ add an Employee. Return a Mono<Integer> response with auto-generated Id
     */
    public Mono<Integer> addEmployee(Employee employee) {
        count++;
        employee.setEmployeeId(count);
        mapOfEmloyeess.put(count, employee);
        return Mono.just(count);
    }

    /**
     * Used to Update the Employee details, Receives the Employee Object and returns the updated Details as
     * Mono<Employee>
     */
    public Mono<Employee> updateEmployee(Employee employee) {
        mapOfEmloyeess.put(employee.getEmployeeId(), employee);
        return Mono.just(employee);
    }

    /**
     * Used to Remove the Employee details, Receives the EmployeeID and  returns the deleted employee Details as
     * Mono<Employee>
     */
    public Mono<Employee> removeEmployee(int id) {
        Employee emp = mapOfEmloyeess.remove(id);
        return Mono.just(emp);
    }

}
