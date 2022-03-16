package com.research.privacy.anonymity.pal.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class ResultsJson {
    private Set<String> quasiColumns;
    private Set<String> columnNames;
    private List<DBRecordWrapper> dbRecordList;
}
