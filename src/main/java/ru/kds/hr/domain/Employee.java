package ru.kds.hr.domain;

import com.google.common.base.Joiner;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.springframework.data.jpa.domain.AbstractPersistable;

import static ru.kds.hr.domain.EmployeeStatus.FIRED;
import static ru.kds.hr.domain.EmployeeStatus.HIRED;

/**
 * Employee
 */
@Entity
public class Employee extends AbstractPersistable<Long> implements Serializable {

    private static final long serialVersionUID = -206638984257962667L;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status = HIRED;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return Joiner.on(" ").useForNull("").join(lastName, middleName, firstName);
    }

    public void fire() {
        this.status = FIRED;
    }
}
