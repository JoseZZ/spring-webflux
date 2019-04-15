/**
 * @author <a href="changeme@ext.inditex.com">Jose Gonzalez</a>
 */

import static org.mockito.BDDMockito.given;

import com.webflux.ApplicationBootUp;
import com.webflux.bussiness.bean.Employee;
import com.webflux.dao.EmployeeDAO;
import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= ApplicationBootUp.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private EmployeeDAO employeeDao;

   /* @Test
    public void givenEmployeeId_whenGetEmployeeById_thenCorrectEmployee() {

        Employee employee = new Employee("Juan", 10006, 1236.5, 1002);

        given(employeeDao.getEmployeeDetailsById(10006)).willReturn(Mono.just(employee));
        testClient.get()
            .uri("/employees/10006")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Employee.class)
            .isEqualTo(employee);
    }

    @Test
    public void whenGetAllEmployees_thenCorrectEmployees() {

        List<Employee> employeeList = new ArrayList<>();

        Employee employee1 = new Employee("Miguel", 10007, 1000, 1002);
        Employee employee2 = new Employee("Pedro", 10008, 2000, 1001);
        Employee employee3 = new Employee("Juana", 10009, 3000, 1003);

        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);

        Flux<Employee> employeeFlux = Flux.fromIterable(employeeList);

        given(employeeDao.getAllEmployee()).willReturn(employeeFlux);
        testClient.get()
            .uri("/employees")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Employee.class)
            .hasSize(3)
            .isEqualTo(employeeList);
    }*/
}
