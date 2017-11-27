package ru.kds.hr.service;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.kds.hr.domain.Employee;
import ru.kds.hr.domain.EmployeeRepository;

/**
 * Employee event listener
 */
@Component
public class EmployeeEventListener {

    private NotificationService notificationService;

    private EmployeeRepository employeeRepository;

    public EmployeeEventListener(NotificationService notificationService, EmployeeRepository employeeRepository) {
        this.notificationService = notificationService;
        this.employeeRepository = employeeRepository;
    }

    @TransactionalEventListener
    @Async
    public void handleEmployeeHiredEvent(EmployeeHiredEvent event) {
        Employee employee = employeeRepository.findOne(event.getEmployeeId());
        if (employee == null) {
            return;
        }
        notificationService.employeeHiredNotification(employee);
    }

    @TransactionalEventListener
    @Async
    public void handleEmployeeFiredEvent(EmployeeFiredEvent event) {
        Employee employee = employeeRepository.findOne(event.getEmployeeId());
        if (employee == null) {
            return;
        }
        notificationService.employeeFiredNotification(employee);
    }
}
