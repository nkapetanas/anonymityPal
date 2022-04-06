package com.research.privacy.anonymity.pal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class QueryResultsResponseJson {
    private Set<String> quasiColumns;
    private Set<String> columnNames;
    private List<DBRecordWrapper> dbRecordList;
}
