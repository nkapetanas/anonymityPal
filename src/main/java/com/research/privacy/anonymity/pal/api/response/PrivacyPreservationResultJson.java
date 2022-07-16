package com.research.privacy.anonymity.pal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PrivacyPreservationResultJson {
    private Boolean privacyCheckResult;
    private String resultMessage;

    public PrivacyPreservationResultJson(Boolean privacyCheckResult) {
        this.privacyCheckResult = privacyCheckResult;
    }
}

