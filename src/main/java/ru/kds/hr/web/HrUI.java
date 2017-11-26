package ru.kds.hr.web;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MVerticalLayout;
import ru.kds.hr.domain.Employee;
import ru.kds.hr.service.EmployeeService;

@SpringUI
@Theme("valo")
public class HrUI extends UI {

    private final EmployeeService employeeService;

    private final EmployeeForm employeeForm;

    private final Button hireButton;

    private MGrid<Employee> employeeGrid;

    public HrUI(EmployeeService employeeService, EmployeeForm employeeForm) {
        this.employeeService = employeeService;
        this.employeeForm = employeeForm;
        this.employeeGrid = new MGrid<>();
        this.hireButton = new Button("Hire", VaadinIcons.PLUS);
    }

    @Override
    protected void init(VaadinRequest request) {
        final MVerticalLayout layout = new MVerticalLayout();
        layout.setWidth(100, Unit.PERCENTAGE);
        setContent(layout);
        Label label = new Label("Employees");
        label.setStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(label);

        hireButton.addClickListener(e -> edit(new Employee()));
        layout.addComponent(hireButton);

        employeeGrid.setWidth(100, Unit.PERCENTAGE);
        employeeGrid.addColumn(Employee::getId).setCaption("ID");
        employeeGrid.addColumn(Employee::getEmail).setCaption("Email");
        employeeGrid.addColumn(Employee::getFullName).setCaption("Name");
        employeeGrid.asSingleSelect().addValueChangeListener(e -> edit(e.getValue()));
        employeeGrid.setDataProvider((sortOrder, offset, limit) -> employeeService.findAll(offset, limit).stream(),
                () -> (int)(employeeService.count()));


        layout.addComponent(employeeGrid);

        //        employeeForm.setChangeHandler(() -> {
        //            employeeForm.setVisible(false);
        //            employeeGrid.markAsDirty();
        //        });


    }

    private void edit(final Employee employee) {
        employeeForm.setEntity(employee);
        employeeForm.openInModalPopup();
    }
}
