package com.research.privacy.anonymity.pal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DBRecordWrapper {
    List<DBRecordKeyValue> dbRecordJsonList;
}
