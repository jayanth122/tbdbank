package org.ece.dto;

import java.io.Serializable;

public enum AccessType implements Serializable {

    MANAGER(1), CUSTOMER(2), EMPLOYEE(3), ADMINISTRATOR(4);
    private final int accessCode;
    AccessType(final int accessCode) {
        this.accessCode = accessCode;
    }
}
