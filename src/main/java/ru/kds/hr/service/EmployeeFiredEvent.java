package ru.kds.hr.service;

import java.io.Serializable;

/**
 * Employee fired event
 */
public class EmployeeFiredEvent implements Serializable {

    private static final long serialVersionUID = 7516715740623928351L;

    /**
     * Employee identifier
     */
    private Long employeeId;

    /**
     * Class constructor
     *
     * @param employeeId employee identifier
     */
    public EmployeeFiredEvent(Long employeeId) {
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
