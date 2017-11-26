package ru.kds.hr.service;

import java.io.Serializable;

/**
 * Employee hired event
 */
public class EmployeeHiredEvent implements Serializable {

    private static final long serialVersionUID = -161066060270033206L;

    /**
     * Employee identifier
     */
    private Long employeeId;

    /**
     * Class constructor
     *
     * @param employeeId employee identifier
     */
    public EmployeeHiredEvent(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Returns employee identifier
     *
     * @return the employee identifier
     */
    public Long getEmployeeId() {
        return employeeId;
    }
}
