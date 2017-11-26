package ru.kds.hr.web;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import ru.kds.hr.domain.Employee;
import ru.kds.hr.service.EmployeeService;
import ru.kds.hr.service.ObjectNotFoundException;

/**
 * Employee editor
 */
@SpringComponent
@UIScope
public class EmployeeForm extends AbstractForm<Employee> {

    private final EmployeeService employeeService;

    TextField email = new TextField("Email");

    TextField lastName = new TextField("Last name");

    TextField firstName = new TextField("First name");

    TextField middleName = new TextField("Middle name");

    Button save = new Button("Save");

    Button cancel = new Button("Cancel");

    Button fire = new Button("Fire");

    CssLayout actions = new CssLayout(save, cancel, fire);

    public EmployeeForm(EmployeeService employeeService) {
        super(Employee.class);
        setModalWindowTitle("Edit employee");
        createResetButton();
        createDeleteButton();
        setDeleteCaption("Fire");
        this.employeeService = employeeService;

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
                }
            }
        });

        setSizeUndefined();
    }

    //    public final void editEmployee(Long employeeId) {
    //        if (employeeId != null) {
    //            Employee employee = null;
    //            try {
    //                employee = employeeService.get(employeeId);
    //            } catch (ObjectNotFoundException e) {
    //                Notification.show("Employee does not exist", "", Notification.Type.ERROR_MESSAGE);
    //                return;
    //            }
    //            email.setValue(Objects.toString(employee.getEmail(), ""));
    //            lastName.setValue(Objects.toString(employee.getLastName(), ""));
    //            firstName.setValue(Objects.toString(employee.getFirstName(), ""));
    //            middleName.setValue(Objects.toString(employee.getMiddleName(), ""));
    //        } else {
    //            email.setValue("");
    //            lastName.setValue("");
    //            firstName.setValue("");
    //            middleName.setValue("");
    //        }
    //        fire.setVisible(employeeId != null);
    //
    //        setVisible(true);
    //
    //        save.focus();
    //        firstName.selectAll();
    //    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(new MFormLayout(email, lastName, firstName, middleName).withWidth(""), getToolbar())
                .withWidth("");
    }
}
