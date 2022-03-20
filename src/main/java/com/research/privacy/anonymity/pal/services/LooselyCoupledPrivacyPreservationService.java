package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.response.QueryResultsJson;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class LooselyCoupledPrivacyPreservationService {

    private static double THRESHOLD_PROBABILITY = 0.5;

    public QueryResultsJson looselyCoupledPrivacyPreservationCheck(QueryResultsJson queryResults) {
        final List<DBRecord> dbRecordList = queryResults.getDbRecordList();
        final Set<String> quasiColumnsToCheck = queryResults.getQuasiColumnsToCheck();

        final Map<String, Set<String>> uniqueValuesPerQuasiColumn = getUniqueValuesPerQuasiColumn(dbRecordList, quasiColumnsToCheck);
        if(quasiColumnsToCheck.size() == 1){

            final boolean isPrivacyPreservedInSingleColumn = checkIsPrivacyPreservedInSingleColumn(uniqueValuesPerQuasiColumn, dbRecordList);
            if(isPrivacyPreservedInSingleColumn){
                queryResults.setPrivacyPreserved(false);
                return queryResults;
            }
            queryResults.setPrivacyPreserved(true);
            return queryResults;
        }
        multiColumnCheck(uniqueValuesPerQuasiColumn, dbRecordList);
        return null;
    }

    private boolean checkIsPrivacyPreservedInSingleColumn(final Map<String, Set<String>> uniqueValuesPerQuasiColumn, final List<DBRecord> dbRecordList) {
        for (Map.Entry<String, Set<String>> entry :  uniqueValuesPerQuasiColumn.entrySet()) {
            final String quasiColumnName = entry.getKey();
            final Set<String> valuesToCheck = entry.getValue();

            return valuesToCheck.stream().anyMatch(value-> dbRecordList.stream().anyMatch(d-> {
                List<DBRecord> filteredDBRecords = new ArrayList<>();
                final List<Attribute> attributes = d.getAttributes();

                attributes.forEach(a-> {
                    if(quasiColumnName.equals(a.getColumnName()) && value.equals(a.getValue())){
                        filteredDBRecords.add(d);
                    }
                });

                return isPrivacyPreserved(filteredDBRecords);
            }));

        }
        return false;
    }

    private void multiColumnCheck(final Map<String, Set<String>> uniqueValuesPerQuasiColumn, final List<DBRecord> dbRecordList) {

    }

    private boolean isPrivacyPreserved(final List<DBRecord> filteredDBRecords) {
        // Case N = 1, we have only one result and so we know what the patient has
        final int numberOfRecords = filteredDBRecords.size();
        if(numberOfRecords == 1){
            return false;
        }

        Set<String> uniqueSensitiveAttributeValues = new HashSet<>();

        filteredDBRecords.forEach(f-> uniqueSensitiveAttributeValues.add(f.getSingleSensitiveRecordValue()));

        if(numberOfRecords >= 2 && uniqueSensitiveAttributeValues.size() == 1){
            return false;
        }

        return uniqueSensitiveAttributeValues.stream().anyMatch(uniqueSensitiveValue -> {
            final int calculatedProbability = calculateProbability(numberOfRecords, uniqueSensitiveValue, filteredDBRecords);
            return THRESHOLD_PROBABILITY <= calculatedProbability;
        });
    }

    private int calculateProbability(final int numberOfRecords,final String uniqueSensitiveValue, final List<DBRecord> filteredDBRecords) {
        AtomicInteger numberOfRecordsWithSensitiveValue  = new AtomicInteger();
        filteredDBRecords.forEach(r->{
            if(uniqueSensitiveValue.equals(r.getSingleSensitiveRecordValue())){
                numberOfRecordsWithSensitiveValue .getAndIncrement();
            }
        });
        return numberOfRecordsWithSensitiveValue.get() / numberOfRecords;
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
