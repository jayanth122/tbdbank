package org.ece.dto;

public enum AccessType {

    MANAGER(1), CUSTOMER(2), EMPLOYEE(3), ADMINISTRATOR(4);
    private final int accessCode;
    AccessType(final int accessCode) {
        this.accessCode = accessCode;
    }
}
