package com.research.privacy.anonymity.pal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuasiKeyPairValue {
    private String quasiColumn;
    private String valueToCheck;
}
