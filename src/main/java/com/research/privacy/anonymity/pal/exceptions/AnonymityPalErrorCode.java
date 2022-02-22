package com.research.privacy.anonymity.pal.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AnonymityPalErrorCode {

    AP_E_0000(0, ErrorMessagesEnum.AP_E_0000, "Unexpected server error."),
    AP_E_0001(1, ErrorMessagesEnum.AP_E_0001, "Database exception occurred"),
    AP_E_0002(2, ErrorMessagesEnum.AP_E_0002, "The query results do not meet the privacy requirements"),
    ;

    @Getter
    private Short techId;

    private ErrorMessagesEnum code;

    @Getter
    private String description;

    AnonymityPalErrorCode(Integer techId, ErrorMessagesEnum code, String description) {
        this.techId = techId.shortValue();
        this.code = code;
        this.description = description;
    }


    public String getCode() {
        return code.getKey();
    }
}

