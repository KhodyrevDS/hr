package ru.kds.hr.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kds.hr.domain.Employee;
import ru.kds.hr.domain.EmployeeRepository;
import ru.kds.hr.domain.EmployeeStatus;

/**
 * Employee service
 */
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EventProducer eventProducer;

    /**
     * Class constructor
     *
     * @param employeeRepository employee repository
     * @param eventProducer event producer
     */
    public EmployeeService(EmployeeRepository employeeRepository, EventProducer eventProducer) {
        this.employeeRepository = employeeRepository;
        this.eventProducer = eventProducer;
    }

    /**
     * Hire employee
     *
     * @param email email
     * @param lastName last name
     * @param firstName first name
     * @param middleName middle name
     * @return the hired employee
     */
    @Transactional
    public Employee hire(String email, String lastName, String firstName, String middleName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(lastName), "parameter 'email' is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(lastName), "parameter 'lastName' is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(firstName), "parameter 'firstName' is empty");

        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setLastName(lastName);
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee = employeeRepository.save(employee);

        eventProducer.createEmployeeHiredEvent(employee);

        return employee;
    }

    /**
     * Get employee
     *
     * @param id employee identifier
     * @return the employee
     * @throws ObjectNotFoundException
     */
    public Employee get(Long id) throws ObjectNotFoundException {
        Employee employee = employeeRepository.findOne(id);
        if (employee == null) {
            throw new ObjectNotFoundException("Employee with id " + id + " does not exist");
        }

        return employee;
    }

    /**
     * Find all
     *
     * @param offset offset
     * @param limit limit
     * @return list of employee
     */
    public List<Employee> findAll(int offset, int limit) {
        return employeeRepository.findAll(new ChunkRequest(offset, limit)).getContent();
    }

    /**
     * Count of all employees
     *
     * @return the number of employees
     */
    public long count() {
        return employeeRepository.count();
    }

    /**
     * Update employee
     *
     * @param id employee identifier
     * @param email email
     * @param lastName last name
     * @param firstName first name
     * @param middleName middle name
     * @return the updated employee
     */
    @Transactional
    public Employee update(Long id, String email, String lastName, String firstName, String middleName)
            throws ObjectNotFoundException {
        Preconditions.checkArgument(id != null, "parameter 'id' is null");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(lastName), "parameter 'email' is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(lastName), "parameter 'lastName' is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(firstName), "parameter 'firstName' is empty");

        Employee employee = get(id);
        employee.setEmail(email);
        employee.setLastName(lastName);
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);

        return employeeRepository.save(employee);
    }

    /**
     * Fire employee
     *
     * @param id employee identifier
     */
    @Transactional
    public void fire(Long id) throws ObjectNotFoundException {
        Preconditions.checkArgument(id != null, "parameter 'id' is null");
        Employee employee = get(id);
        if (employee.getStatus() == EmployeeStatus.FIRED) {
            return;
        }
        employee.fire();

        employeeRepository.save(employee);

        eventProducer.createEmployeeFiredEvent(employee);
    }
}
