package ru.kds.hr.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.kds.hr.domain.Employee;

/**
 * Event producer
 */
@Component
public class EventProducer {

    private ApplicationEventPublisher eventPublisher;

    /**
     * Class constructor
     *
     * @param eventPublisher application event publisher
     */
    public EventProducer(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Create employee hired event
     *
     * @param employee hired employee
     */
    public void createEmployeeHiredEvent(Employee employee) {
        eventPublisher.publishEvent(new EmployeeHiredEvent(employee.getId()));
    }

    /**
     * Create employee fired event
     *
     * @param employee fired employee
     */
    public void createEmployeeFiredEvent(Employee employee) {
        eventPublisher.publishEvent(new EmployeeFiredEvent(employee.getId()));
    }
}
