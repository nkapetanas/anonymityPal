package com.research.privacy.anonymity.pal.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AnonymityPalErrorCode {

    AP_E_0000(0, ErrorMessagesEnum.AP_E_0000, "Unexpected server error."),
    AP_E_0001(1, ErrorMessagesEnum.AP_E_0001, "Database exception occurred"),
    AP_E_0002(2, ErrorMessagesEnum.AP_E_0002, "The query results do not meet the privacy requirements"),
    AP_E_0003(3, ErrorMessagesEnum.AP_E_0003, "The selected DB is empty"),
    AP_E_0004(4, ErrorMessagesEnum.AP_E_0004, "The selected table is empty"),
    AP_E_0005(5, ErrorMessagesEnum.AP_E_0005, "Join info is missing"),
    AP_E_0006(6, ErrorMessagesEnum.AP_E_0006, "Join table is empty"),
    AP_E_0007(7, ErrorMessagesEnum.AP_E_0007, "One or both of the join columns are invalid"),
    AP_E_0008(8, ErrorMessagesEnum.AP_E_0008, "The quasi column/s to check or the values are empty"),
    AP_E_0009(9, ErrorMessagesEnum.AP_E_0009, "The Rule 1 holds. Possible breach of confidentiality detected"),
    AP_E_0010(10, ErrorMessagesEnum.AP_E_0010, "The Rule 2 holds. Possible breach of confidentiality detected"),
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

