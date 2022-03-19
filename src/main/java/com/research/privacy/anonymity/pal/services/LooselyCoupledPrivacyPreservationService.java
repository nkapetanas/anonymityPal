package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.response.QueryResultsJson;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class LooselyCoupledPrivacyPreservationService {


    public QueryResultsJson looselyCoupledPrivacyPreservationCheck(final QueryResultsJson queryResults) {
        final List<DBRecord> dbRecordList = queryResults.getDbRecordList();
        final Set<String> quasiColumnsToCheck = queryResults.getQuasiColumnsToCheck();

        final Map<String, Set<String>> uniqueValuesPerQuasiColumn = getUniqueValuesPerQuasiColumn(dbRecordList, quasiColumnsToCheck);
        if(uniqueValuesPerQuasiColumn.size() == 1){
            if(singleColumnCheck(uniqueValuesPerQuasiColumn, dbRecordList)){
                return null;
            }
            return null;
        }
        multiColumnCheck(uniqueValuesPerQuasiColumn, dbRecordList);
        return null;
    }

    private void multiColumnCheck(final Map<String, Set<String>> uniqueValuesPerQuasiColumn, final List<DBRecord> dbRecordList) {

    }

    private boolean singleColumnCheck(final Map<String, Set<String>> uniqueValuesPerQuasiColumn, final List<DBRecord> dbRecordList) {
        for (Map.Entry<String, Set<String>> entry :  uniqueValuesPerQuasiColumn.entrySet()) {
            final String quasiColumnName = entry.getKey();
            final Set<String> valuesToCheck = entry.getValue();

            List<DBRecord> filteredDBRecords = new ArrayList<>();

            valuesToCheck.forEach(value-> dbRecordList.forEach(d-> {
                final List<Attribute> attributes = d.getAttributes();

                attributes.forEach(a-> {
                    if(quasiColumnName.equals(a.getColumnName()) && value.equals(a.getValue())){
                        filteredDBRecords.add(d);
                    }
                });
            }));
            if(!isPrivacyPreserved(filteredDBRecords)){
                return false;
            }
        }
        return false;
    }

    private boolean isPrivacyPreserved(final List<DBRecord> filteredDBRecords) {
        return false;
    }

    private Map<String, Set<String>> getUniqueValuesPerQuasiColumn(List<DBRecord> dbRecordList, Set<String> quasiColumnsToCheck) {
        Map<String, Set<String>> quasiColumnListOfUniqueValuePairs = new HashMap<>();

        quasiColumnsToCheck.forEach(quasiColumnToCheck->{
            Set<String> uniqueValuesOfQuasiColumnToCheck = new HashSet<>();

            dbRecordList.forEach(dbRecord -> {
                final List<Attribute> attributes = dbRecord.getAttributes();
                attributes.forEach(a-> {
                    if(quasiColumnToCheck.equals(a.getColumnName())){
                        uniqueValuesOfQuasiColumnToCheck.add(String.valueOf(a.getValue()));
                    }
                });
            });

            quasiColumnListOfUniqueValuePairs.put(quasiColumnToCheck, uniqueValuesOfQuasiColumnToCheck);
        });
        return quasiColumnListOfUniqueValuePairs;
    }
}
