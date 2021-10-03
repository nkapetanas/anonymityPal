package com.research.privacy.anonymity.pal.exceptions;

public enum ErrorMessagesEnum {

    AP_E_0000("AP_E_0000"),
    AP_E_0001("AP_E_0001"),
    ;

    private String key;

    ErrorMessagesEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
