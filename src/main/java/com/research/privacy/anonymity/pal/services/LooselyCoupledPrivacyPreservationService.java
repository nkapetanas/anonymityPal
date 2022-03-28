package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.response.QuasiKeyPairValue;
import com.research.privacy.anonymity.pal.api.response.QueryResultsJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.Attribute;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LooselyCoupledPrivacyPreservationService {

    private static final double THRESHOLD_PROBABILITY = 0.5;

    public QueryResultsJson looselyCoupledPrivacyPreservationCheck(QueryResultsJson queryResults) throws AnonymityPalException {
        final List<DBRecord> dbRecordList = queryResults.getDbRecordList();
        final Set<QuasiKeyPairValue> quasiColumnsToCheck = queryResults.getQuasiColumnsToCheck();

        final boolean isPrivacyPreservedInSingleColumn = isPrivacyPreserved(quasiColumnsToCheck, dbRecordList);
        if (isPrivacyPreservedInSingleColumn) {
            queryResults.setPrivacyPreserved(true);
            return queryResults;

        }
        queryResults.setPrivacyPreserved(false);
        return queryResults;
    }

    private boolean isPrivacyPreserved(final Set<QuasiKeyPairValue> quasiKeyPairValues, final List<DBRecord> dbRecordList) throws AnonymityPalException {
        if (Utils.isEmpty(quasiKeyPairValues)) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0008);
        }

        final List<Boolean> validationResults = quasiKeyPairValues.stream().map(keyPairValue -> {
            List<DBRecord> filteredDBRecords = filterPerQuasiColumnValue(keyPairValue.getQuasiColumn(), keyPairValue.getValueToCheck(), dbRecordList);
            return isPrivacyPreserved(filteredDBRecords);
        }).collect(Collectors.toList());

        return validationResults.stream().noneMatch(v -> v.equals(false));
    }

    private List<DBRecord> filterPerQuasiColumnValue(final String quasiColumnName, final String columnValue, final List<DBRecord> dbRecordList) {
        List<DBRecord> filteredDBRecords = new ArrayList<>();
        dbRecordList.forEach(d -> {
            final List<Attribute> attributes = d.getAttributes();

            attributes.forEach(a -> {
                if (quasiColumnName.equals(a.getColumnName()) && columnValue.equals(a.getValue())) {
                    filteredDBRecords.add(d);
                }
            });
        });
        return filteredDBRecords;
    }

    private boolean isPrivacyPreserved(final List<DBRecord> filteredDBRecords) {
        // In case N = 1, we have only one result, and so we know what the patient has
        final int numberOfRecords = filteredDBRecords.size();
        if (numberOfRecords == 1) {
            return false;
        }

        Set<String> uniqueSensitiveAttributeValues = new HashSet<>();
        filteredDBRecords.forEach(f -> uniqueSensitiveAttributeValues.add(f.getSingleSensitiveRecordValue()));

        if (numberOfRecords >= 2 && uniqueSensitiveAttributeValues.size() == 1) {
            return false;
        }

        return uniqueSensitiveAttributeValues.stream().noneMatch(uniqueSensitiveValue -> THRESHOLD_PROBABILITY <= calculateProbability(numberOfRecords, uniqueSensitiveValue, filteredDBRecords));
    }

    private double calculateProbability(final int numberOfRecords, final String uniqueSensitiveValue, final List<DBRecord> filteredDBRecords) {
        AtomicInteger numberOfRecordsWithSensitiveValue = new AtomicInteger();
        filteredDBRecords.forEach(r -> {
            if (uniqueSensitiveValue.equals(r.getSingleSensitiveRecordValue())) {
                numberOfRecordsWithSensitiveValue.getAndIncrement();
            }
        });
        return Math.round((double) numberOfRecordsWithSensitiveValue.get() / (double) numberOfRecords * 100) / 100.f;
    }
}
