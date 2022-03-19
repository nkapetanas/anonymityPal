package com.research.privacy.anonymity.pal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DBRecordKeyValue {
    private String columnName;
    private String recordValue;
}
