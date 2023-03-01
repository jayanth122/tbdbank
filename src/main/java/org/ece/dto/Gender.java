package org.ece.dto;

import java.io.Serializable;

public enum Gender implements Serializable {
    MALE(1), FEMALE(2), OTHER(3);
    private final int accessCode;
    Gender(final int accessCode) {
        this.accessCode = accessCode;
    }
}
