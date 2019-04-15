package com.webflux.web.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import com.webflux.bussiness.bean.Employee;
import com.webflux.dao.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Controller
public class EmployeeHandler {

    @Autowired
    private EmployeeDAO employeeDAO;

	/*
		Las peticiones se gestionan mediante funciones handler, una alternavita a los tipicos @Controller
		Cada funcion handler tiene como parametro un ServerRequest y como respuesta devolvera un Mono o Flux
		- Mono: Stream que puede emitir 0 o 1 elemento
		- Flux: Stream que puede emitir 0 o mas elementos
	 */


    /**
     * Receives a ServerRequest. Invokes the method getAllEmployee() from EmployeeDAO. Prepares a Mono<ServerResponse>
     * and returns the same.
     */
    public Mono<ServerResponse> getEmployeeDetails(ServerRequest request) {
        Flux<Employee> res = employeeDAO.getAllEmployee();
        return ServerResponse.ok().
            contentType(MediaType.APPLICATION_JSON).body(res, Employee.class);
    }


    /**
     * Receives a ServerRequest. Extracts the Path Variable (named id) from the Request. Invokes the method
     * [getEmployeeDetailsById()] from EmployeeDAO. Verifies if the object returned in the previous step is null then
     * returns a Bad request with appropriate message. Else Returns the Mono<ServerResponse> with the Employee Data.
     */
    public Mono<ServerResponse> getEmployeeDetailByEmployeeId(ServerRequest
        request) {
        //Extracts the Path Variable id from the Request
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<Employee> employee = employeeDAO.getEmployeeDetailsById(id);
        Mono<ServerResponse> res = null;
        if (employee == null) {
            res = ServerResponse.badRequest().body(fromObject("Please give a valid employee Id"));
        } else {
            //Converting Mono of Mono<Employee> type to Mono<ServerResponse>
            res = employee.flatMap(x -> ServerResponse.ok().body(fromObject(x)));
        }
        return res;
    }

    /**
     * Receives a ServerRequest. Makes use of BodyExtractors and Extracts the Employee Data as Mono<Employee> from the
     * ServerRequest. Invokes the method [addEmployee()] of the EmployeeDAO. Prepares a Mono<ServerResponse> and returns
     * the same.
     */
    public Mono<ServerResponse> addEmployee(ServerRequest request) {
        Mono<Employee> requestBodyMono =
            request.body(BodyExtractors.toMono(Employee.class));

        Mono<Integer> mono = employeeDAO.addEmployee(requestBodyMono.block());

        //Converting Mono of Mono<Employee> type to Mono<ServerResponse>
        Mono<ServerResponse> res = mono.flatMap(x -> ServerResponse.ok().body
            (fromObject("Employee Created with Id" + x)));
        return res;
    }


    /**
     * Receives a ServerRequest. Makes use of BodyExtractors and Extracts the Employee Data as Mono<Employee> from the
     * ServerRequest. Finds the Employee and updates the details by invoking updateEmployee() of EmployeeDAO. Prepares a
     * Mono<ServerResponse> and returns the same.
     */
    public Mono<ServerResponse> updateEmployee(ServerRequest request) {
        Mono<Employee> requestBodyMono = request.body
            (BodyExtractors.toMono(Employee.class));
        Employee employee = requestBodyMono.block();
        Mono<Employee> employeeRet = employeeDAO.getEmployeeDetailsById
            (employee.getEmployeeId());
        Mono<ServerResponse> res = null;
        if (employeeRet == null) {
            res = ServerResponse.badRequest().body
                (fromObject("Please Give valid employee details to update"));
        } else {
            Mono<Employee> emp = employeeDAO.updateEmployee(employee);

            //Converting Mono of Mono<Employee> type to Mono<ServerResponse>
            res = emp.flatMap(x -> ServerResponse.ok().body(fromObject(x)));
        }
        return res;
    }

    /**
     * Receives a ServerRequest. Makes use of BodyExtractors and Extracts the Employee Data as Mono<Employee> from the
     * ServerRequest. Finds the Employee and deletes the details by invoking removeEmployee() of EmployeeDAO. Prepares a
     * Mono<ServerResponse> and returns the same.
     */
    public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
        int myId = Integer.parseInt(request.pathVariable("id"));
        Mono<ServerResponse> res = null;
        if (employeeDAO.getEmployeeDetailsById(myId) == null) {
            res = ServerResponse.badRequest().body
                (fromObject("Please Give valid employee details to delete"));
        } else {
            Mono<Employee> employee = employeeDAO.removeEmployee(myId);

            //Converting Mono of Mono<Employee> type to Mono<ServerResponse>
            res = employee.flatMap(x -> ServerResponse.ok().body(fromObject(x)));

        }
        return res;
    }
}
