package com.research.privacy.anonymity.pal.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnonymityPalException extends Exception {
    private static final long serialVersionUID = 1L;

    private AnonymityPalErrorCode anonymityPalErrorCode;
    private String details;

    public AnonymityPalException(String message) {
        super(message);
    }

    public AnonymityPalException(AnonymityPalErrorCode anonymityPalErrorCode) {
        super();
        this.anonymityPalErrorCode = anonymityPalErrorCode;
        this.details = anonymityPalErrorCode.getDescription();
    }
}
