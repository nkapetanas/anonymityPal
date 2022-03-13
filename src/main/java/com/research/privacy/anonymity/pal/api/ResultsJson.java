package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.dataset.DBRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class ResultsJson {
    private Set<String> quasiColumns;
    private List<DBRecord> dbRecordList;
}
