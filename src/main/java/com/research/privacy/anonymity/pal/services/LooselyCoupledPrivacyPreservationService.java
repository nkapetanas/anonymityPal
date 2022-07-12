package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import com.research.privacy.anonymity.pal.api.response.PrivacyPreservationResultJson;
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

import static com.research.privacy.anonymity.pal.services.PrestoService.transformToDBRecords;

@Slf4j
@Service
public class LooselyCoupledPrivacyPreservationService {

    private static final double THRESHOLD_PROBABILITY = 0.5;

    public PrivacyPreservationResultJson looselyCoupledPrivacyPreservationCheck(QueryResultsJson queryResults) throws AnonymityPalException {
        final List<DBRecordWrapper> dbRecordJsonList = queryResults.getDbRecordList();
        final List<DBRecord> dbRecordList = transformToDBRecords(dbRecordJsonList);
        final Set<QuasiKeyPairValue> quasiColumnsToCheck = queryResults.getQuasiColumnsToCheck();

        return checkPrivacyPreservation(quasiColumnsToCheck, dbRecordList);
    }

    private PrivacyPreservationResultJson checkPrivacyPreservation(final Set<QuasiKeyPairValue> quasiKeyPairValues, final List<DBRecord> dbRecordList) throws AnonymityPalException {
        if (Utils.isEmpty(quasiKeyPairValues)) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0008);
        }

        for (QuasiKeyPairValue keyPairValue : quasiKeyPairValues) {
            List<DBRecord> filteredDBRecords = filterPerQuasiColumnValue(keyPairValue.getQuasiColumn(), keyPairValue.getValueToCheck(), dbRecordList);

            try {
                checkPrivacyPreservation(filteredDBRecords);
            } catch (AnonymityPalException e) {
                return new PrivacyPreservationResultJson(false, e.getDetails());
            }
        }

        return new PrivacyPreservationResultJson(true);
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

    private void checkPrivacyPreservation(final List<DBRecord> filteredDBRecords) throws AnonymityPalException {
        // In case N = 1, we have only one result, and so we know what the patient has
        final int numberOfRecords = filteredDBRecords.size();
        if (numberOfRecords == 1) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0009);
        }

        HashMap<String, Integer> uniqueSensitiveAttributeValues = new HashMap<>();
        filteredDBRecords.forEach(f -> {
            final String recordsSensitiveValue = f.getSingleSensitiveRecordValue();

            if (uniqueSensitiveAttributeValues.containsKey(recordsSensitiveValue)) {
                final int numberOfTimesExists = uniqueSensitiveAttributeValues.get(recordsSensitiveValue) + 1;
                uniqueSensitiveAttributeValues.put(recordsSensitiveValue, numberOfTimesExists);
            } else {
                uniqueSensitiveAttributeValues.put(recordsSensitiveValue, 1);
            }
        });

        if (numberOfRecords >= 2 && uniqueSensitiveAttributeValues.size() == 1) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0009);
        }

        for (Map.Entry<String, Integer> uniqueSensitiveAttributeValue : uniqueSensitiveAttributeValues.entrySet()) {
            int numberOfRecordsWithSensitiveValue = uniqueSensitiveAttributeValue.getValue();
            if (THRESHOLD_PROBABILITY <= calculateProbability(numberOfRecords, numberOfRecordsWithSensitiveValue)) {
                throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0010);
            }
        }
    }

    private double calculateProbability(final int numberOfRecords, final int numberOfRecordsWithSensitiveValue) {
        return Math.round((double) numberOfRecordsWithSensitiveValue / (double) numberOfRecords * 100) / 100.f;
    }
}
