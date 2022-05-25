package com.research.privacy.anonymity.pal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DBRecordKeyValue {
    private String columnName;
    private String recordValue;
}
