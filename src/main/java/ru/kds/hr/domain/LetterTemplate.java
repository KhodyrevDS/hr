package ru.kds.hr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Letter template
 */
@Entity
public class LetterTemplate extends AbstractPersistable<Long> {

    public static final String EMPLOYEE_HIRED = "EMPLOYEE_HIRED";

    public static final String EMPLOYEE_FIRED = "EMPLOYEE_FIRED";

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
