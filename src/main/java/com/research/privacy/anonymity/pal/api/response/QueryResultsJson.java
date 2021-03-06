package com.research.privacy.anonymity.pal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueryResultsJson {
    private List<DBRecordWrapper> dbRecordList;
    private Set<QuasiKeyPairValue> quasiColumnsToCheck;
    private boolean isPrivacyPreserved;
}
