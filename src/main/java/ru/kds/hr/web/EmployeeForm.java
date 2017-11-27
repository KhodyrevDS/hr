package ru.kds.hr.web;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import org.springframework.context.ApplicationEventPublisher;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import ru.kds.hr.domain.Employee;
import ru.kds.hr.domain.EmployeeStatus;
import ru.kds.hr.service.EmployeeService;
import ru.kds.hr.service.ObjectNotFoundException;

/**
 * Employee editor
 */
@SpringComponent
@UIScope
public class EmployeeForm extends AbstractForm<Employee> {

    private static final long serialVersionUID = -676968759674741282L;

    private final EmployeeService employeeService;

    private final ApplicationEventPublisher eventPublisher;

    private final TextField email = new TextField("Email");

    private final TextField lastName = new TextField("Last name");

    private final TextField firstName = new TextField("First name");

    private final TextField middleName = new TextField("Middle name");

    public EmployeeForm(EmployeeService employeeService, ApplicationEventPublisher eventPublisher) {
        super(Employee.class);
        setModalWindowTitle("Edit employee");
        setDeleteCaption("Fire");
        this.employeeService = employeeService;
        this.eventPublisher = eventPublisher;

        setSavedHandler(employee -> {
            if (employee.isNew()) {
                employeeService.hire(employee.getEmail(), employee.getLastName(), employee.getFirstName(),
                        employee.getMiddleName());
            } else {
                try {
                    employeeService.update(employee.getId(), employee.getEmail(), employee.getLastName(),
                            employee.getFirstName(), employee.getMiddleName());
                } catch (ObjectNotFoundException e1) {
                    Notification.show("Employee does not exist", "", Notification.Type.ERROR_MESSAGE);
                    return;
                }
            }
            eventPublisher.publishEvent(new EmployeeModifiedEvent(employee));
            this.closePopup();
        });

        setDeleteHandler(employee -> {
            try {
                employeeService.fire(employee.getId());
                eventPublisher.publishEvent(new EmployeeModifiedEvent(employee));
            } catch (ObjectNotFoundException e) {
                Notification.show("Employee does not exist", "", Notification.Type.ERROR_MESSAGE);
                return;
            }
            this.closePopup();
        });

        setResetHandler(entity -> {
            this.closePopup();
        });
        setSizeUndefined();
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(new MFormLayout(email, lastName, firstName, middleName).withWidth(""), getToolbar())
                .withWidth("");
    }

    @Override
    public Window openInModalPopup() {
        getDeleteButton().setVisible(
                getEntity() != null && !getEntity().isNew() && getEntity().getStatus() != EmployeeStatus.FIRED);
        return super.openInModalPopup();
    }
}
