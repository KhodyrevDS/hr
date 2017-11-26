package ru.kds.hr.service;

/**
 * Object not found exception
 */
public class ObjectNotFoundException extends ServiceException {

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
