package com.research.privacy.anonymity.pal.exceptions;

public enum ErrorMessagesEnum {

    AP_E_0000("AP_E_0000"),
    AP_E_0001("AP_E_0001"),
    AP_E_0002("AP_E_0002"),
    AP_E_0003("AP_E_0003"),
    AP_E_0004("AP_E_0004"),
    AP_E_0005("AP_E_0005"),
    AP_E_0006("AP_E_0006"),
    AP_E_0007("AP_E_0007"),
    AP_E_0008("AP_E_0008"),
    AP_E_0009("AP_E_0009"),
    AP_E_0010("AP_E_0010"),
    ;

    private String key;

    ErrorMessagesEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
