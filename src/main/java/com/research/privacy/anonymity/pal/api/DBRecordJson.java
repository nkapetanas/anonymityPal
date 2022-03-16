package com.research.privacy.anonymity.pal.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DBRecordJson {
    private String columnName;
    private String recordValue;
}
