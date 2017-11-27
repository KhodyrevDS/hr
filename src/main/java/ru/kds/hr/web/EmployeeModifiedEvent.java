package ru.kds.hr.web;

import ru.kds.hr.domain.Employee;

/**
 * Employee modified event
 */
public class EmployeeModifiedEvent {

    private final Employee employee;

    public EmployeeModifiedEvent(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
