package ru.kds.hr.service

import org.springframework.data.domain.PageImpl
import ru.kds.hr.domain.Employee
import ru.kds.hr.domain.EmployeeRepository
import ru.kds.hr.domain.EmployeeStatus
import spock.lang.Specification

/**
 * Specification for {@link EmployeeService}
 */
class EmployeeServiceSpec extends Specification {

    private EmployeeRepository employeeRepository = Mock(EmployeeRepository)

    private EventProducer eventProducer = Mock(EventProducer)

    private EmployeeService employeeService = new EmployeeService(employeeRepository, eventProducer)

    def 'should hire new employee'() {
        given:
        String email = 'new@example.com'
        String lastName = 'lastName'
        String firstName = 'firstName'
        String middleName = 'middleName'

        when:
        Employee employee = employeeService.hire(email, lastName, firstName, middleName)

        then:
        1 * employeeRepository.save(_ as Employee) >> { Employee toSave -> toSave }
        1 * eventProducer.createEmployeeHiredEvent(_ as Employee)

        and:
        employee.email == email
        employee.lastName == lastName
        employee.firstName == firstName
        employee.middleName == middleName
        employee.status == EmployeeStatus.HIRED
    }

    def 'should get employee by id'() {
        given:
        Long id = 22;
        Employee givenEmployee = new Employee();

        when:
        Employee employee = employeeService.get(id)

        then:
        1 * employeeRepository.findOne(id) >> givenEmployee

        and:
        employee == givenEmployee
    }

    def 'get employee should throw ObjectNotFoundException if employee does not exist'() {
        given:
        Long id = 22;

        when:
        Employee employee = employeeService.get(id)

        then:
        1 * employeeRepository.findOne(id) >> null

        thrown ObjectNotFoundException
    }

    def 'should find all employees'() {
        given:
        int offset = 40
        int limit = 20

        List<Employee> givenEmployees = new LinkedList<>();

        when:
        List<Employee> employees = employeeService.findAll(offset, limit)

        then:
        1 * employeeRepository.findAll(_ as ChunkRequest) >> new PageImpl<Employee>(givenEmployees);

        and:
        employees == givenEmployees
    }

    def 'should return the number of employees'() {
        given:
        int givenCount = 22

        when:
        long count = employeeService.count()

        then:
        1 * employeeRepository.count() >> givenCount

        and:
        count == givenCount
    }

    def 'should update employee'() {
        given:
        Employee givenEmployee = new Employee()
        givenEmployee.id = 33
        givenEmployee.email = 'old@example.com'
        givenEmployee.lastName = 'oldLastName'
        givenEmployee.firstName = 'oldFirstName'
        givenEmployee.middleName = 'oldMiddleName'

        String email = 'new@example.com'
        String lastName = 'lastName'
        String firstName = 'firstName'
        String middleName = 'middleName'

        when:
        Employee employee = employeeService.update(givenEmployee.id, email, lastName, firstName, middleName)

        then:
        1 * employeeRepository.findOne(givenEmployee.id) >> givenEmployee
        1 * employeeRepository.save(givenEmployee) >> { Employee toSave -> toSave }

        and:
        employee.email == email
        employee.lastName == lastName
        employee.firstName == firstName
        employee.middleName == middleName
        employee.status == EmployeeStatus.HIRED
    }

    def 'update employee should throw ObjectNotFoundException if employee does not exist'() {
        given:
        Employee givenEmployee = new Employee()
        givenEmployee.id = 33

        String email = 'new@example.com'
        String lastName = 'lastName'
        String firstName = 'firstName'
        String middleName = 'middleName'

        when:
        Employee employee = employeeService.update(givenEmployee.id, email, lastName, firstName, middleName)

        then:
        1 * employeeRepository.findOne(givenEmployee.id) >> null
        0 * employeeRepository.save(_)

        and:
        thrown ObjectNotFoundException
    }

    def 'should fire employee'() {
        given:
        Employee givenEmployee = new Employee()
        givenEmployee.id = 33
        givenEmployee.email = 'old@example.com'
        givenEmployee.lastName = 'oldLastName'
        givenEmployee.firstName = 'oldFirstName'
        givenEmployee.middleName = 'oldMiddleName'

        Employee employee

        when:
        employeeService.fire(givenEmployee.id)

        then:
        1 * employeeRepository.findOne(givenEmployee.id) >> givenEmployee
        1 * employeeRepository.save(givenEmployee) >> { Employee toSave -> employee = toSave }
        1 * eventProducer.createEmployeeFiredEvent(givenEmployee);

        and:
        employee.email == 'old@example.com'
        employee.lastName == 'oldLastName'
        employee.firstName == 'oldFirstName'
        employee.middleName == 'oldMiddleName'
        employee.status == EmployeeStatus.FIRED
    }

    def 'fire employee should throw ObjectNotFoundException if employee does not exist'() {
        given:
        Employee givenEmployee = new Employee()
        givenEmployee.id = 33

        when:
        employeeService.fire(givenEmployee.id)

        then:
        1 * employeeRepository.findOne(givenEmployee.id) >> null
        0 * employeeRepository.save(_)
        0 * eventProducer.createEmployeeFiredEvent(_);

        and:
        thrown ObjectNotFoundException
    }
}
