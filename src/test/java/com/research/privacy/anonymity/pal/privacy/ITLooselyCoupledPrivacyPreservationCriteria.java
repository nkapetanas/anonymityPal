package com.research.privacy.anonymity.pal.privacy;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.response.*;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.services.LooselyCoupledPrivacyPreservationService;
import com.research.privacy.anonymity.pal.services.PrivacyService;
import com.research.privacy.anonymity.pal.utils.AnonymizedHealthSampleDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest(classes = Application.class)
class ITLooselyCoupledPrivacyPreservationCriteria {

    private static final String COLUMN_NAME_ZIPCODE = "zipcode";
    private static final String COLUMN_NAME_AGE = "age";
    private static final String COLUMN_NAME_GENDER = "gender";
    private static final String COLUMN_NAME_NATIONALITY = "nationality";
    private static final String COLUMN_NAME_MARITAL_STATUS = "marital_status";
    private static final String COLUMN_NAME_BLOOD_TYPE = "blood_type";
    private static final String COLUMN_NAME_HEALTH_CONDITION = "health_condition";

    private static final String SUPPRESSED_ZIP_CODE_1 = "130**";
    private static final String SUPPRESSED_ZIP_CODE_2 = "150**";
    private static final String SUPPRESSED_ZIP_CODE_3 = "160**";

    private static final String MALE = "M";
    private static final String FEMALE = "F";
    private static final String SINGLE = "Single";
    private static final String MARRIED = "Married";

    private static final String SUPPRESSED_AGE_1 = "20-30";
    private static final String SUPPRESSED_AGE_2 = "50-60";
    private static final String SUPPRESSED_AGE_3 = "60-70";

    private static final String BLOOD_TYPE_A = "A";
    private static final String BLOOD_TYPE_B = "B";
    private static final String BLOOD_TYPE_O = "O";
    private static final String BLOOD_TYPE_AB = "AB";

    private static final String NATIONALITY_1 = "European";

    private static final String HEALTH_CONDITION_1 = "Diabetes";
    private static final String HEALTH_CONDITION_2 = "Cardiovascular";
    private static final String HEALTH_CONDITION_3 = "HIV";
    private static final String HEALTH_CONDITION_4 = "Broken Arm";

    @Autowired
    LooselyCoupledPrivacyPreservationService looselyCoupledPrivacyPreservationService;

    @Autowired
    PrivacyService privacyService;

    @Test
    void looselyCoupledPrivacyPreservationService_OK() throws AnonymityPalException {
        final DBRecordWrapper dbRecord1 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecordWrapper dbRecord2 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecordWrapper dbRecord3 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        Set<QuasiKeyPairValue> quasiColumnsToCheck = new HashSet<>(Collections.singletonList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, MARRIED)));

        List<DBRecordWrapper> dbRecordWrapperList = Utils.asList(dbRecord1, dbRecord2, dbRecord3);

        QueryResultsJson resultsJson = new QueryResultsJson(dbRecordWrapperList, quasiColumnsToCheck, false);

        PrivacyPreservationResultJson privacyPreservationResultJson = looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson);

        Assertions.assertFalse(privacyPreservationResultJson.getPrivacyCheckResult());

        Assertions.assertEquals(AnonymityPalErrorCode.AP_E_0009.getDescription(), privacyPreservationResultJson.getResultMessage());

        quasiColumnsToCheck = new HashSet<>(Collections.singletonList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, SINGLE)));

        resultsJson = new QueryResultsJson(dbRecordWrapperList, quasiColumnsToCheck, false);

        privacyPreservationResultJson = looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson);

        Assertions.assertFalse(privacyPreservationResultJson.getPrivacyCheckResult());
        Assertions.assertEquals(AnonymityPalErrorCode.AP_E_0010.getDescription(), privacyPreservationResultJson.getResultMessage());
    }

    @Test
    void looselyCoupledPrivacyPreservationService_NOK() throws AnonymityPalException {

        // Diabetes records
        final DBRecordWrapper dbRecord1 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecordWrapper dbRecord2 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, FEMALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecordWrapper dbRecord3 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecordWrapper dbRecord4 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, FEMALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        // Heart Disease
        final DBRecordWrapper dbRecord5 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecordWrapper dbRecord6 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, FEMALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecordWrapper dbRecord7 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, FEMALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_O),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecordWrapper dbRecord8 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecordWrapper dbRecord9 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecordWrapper dbRecord10 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, FEMALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_O),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        // HIV Disease
        final DBRecordWrapper dbRecord11 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, FEMALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_O),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecordWrapper dbRecord12 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        // Broken Arm
        final DBRecordWrapper dbRecord13 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecordWrapper dbRecord14 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_3),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));
        final DBRecordWrapper dbRecord15 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, FEMALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecordWrapper dbRecord16 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_3),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, FEMALE),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        List<DBRecordWrapper> dbRecordList = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4, dbRecord5, dbRecord6,
                dbRecord7, dbRecord8, dbRecord9, dbRecord10, dbRecord11, dbRecord12, dbRecord13, dbRecord14, dbRecord15, dbRecord16);

        Set<QuasiKeyPairValue> quasiColumnsToCheck = new HashSet<>(Collections.singletonList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, SINGLE)));

        QueryResultsJson resultsJson = new QueryResultsJson(dbRecordList, quasiColumnsToCheck, false);
        PrivacyPreservationResultJson privacyPreservationResultJson = looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson);

        Assertions.assertFalse(privacyPreservationResultJson.getPrivacyCheckResult());
        Assertions.assertEquals(AnonymityPalErrorCode.AP_E_0010.getDescription(), privacyPreservationResultJson.getResultMessage());

        // We expect the privacy to be broken when checking for the blood type column
        quasiColumnsToCheck = new HashSet<>(Utils.asList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new QuasiKeyPairValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB)));

        resultsJson = new QueryResultsJson(dbRecordList, quasiColumnsToCheck, false);
        privacyPreservationResultJson = looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson);

        Assertions.assertFalse(privacyPreservationResultJson.getPrivacyCheckResult());
        Assertions.assertEquals(AnonymityPalErrorCode.AP_E_0009.getDescription(), privacyPreservationResultJson.getResultMessage());
    }

    @Test
    void looselyCoupledPrivacyPreservationService_many_results_OK() throws AnonymityPalException {
        final List<DBRecordWrapper> dbRecords = AnonymizedHealthSampleDataGenerator.generateDBRecordWrapperData(3, 100);
        Set<QuasiKeyPairValue> quasiColumnsToCheck = new HashSet<>(Collections.singletonList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, MARRIED)));

        QueryResultsJson resultsJson = new QueryResultsJson(dbRecords, quasiColumnsToCheck, false);
        final PrivacyPreservationResultJson privacyPreservationResultJson = looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson);

        Assertions.assertTrue(privacyPreservationResultJson.getPrivacyCheckResult());
        Assertions.assertNull(privacyPreservationResultJson.getResultMessage());
    }
}