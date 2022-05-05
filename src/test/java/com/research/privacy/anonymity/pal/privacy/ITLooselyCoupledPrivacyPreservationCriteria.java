package com.research.privacy.anonymity.pal.privacy;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.response.QuasiKeyPairValue;
import com.research.privacy.anonymity.pal.api.response.QueryResultsJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.TextAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;
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
        final DBRecord dbRecord1 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord2 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord3 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        List<DBRecord> dbRecordList = Utils.asList(dbRecord1, dbRecord2, dbRecord3);
        Assertions.assertFalse(privacyService.isPrivacyModelFulfilled(dbRecordList));

        Set<QuasiKeyPairValue> quasiColumnsToCheck = new HashSet<>(Collections.singletonList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, MARRIED)));

        QueryResultsJson resultsJson = new QueryResultsJson(dbRecordList, quasiColumnsToCheck, false);
        Assertions.assertFalse(looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson));

        quasiColumnsToCheck = new HashSet<>(Collections.singletonList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, SINGLE)));

        resultsJson = new QueryResultsJson(dbRecordList, quasiColumnsToCheck, false);
        Assertions.assertFalse(looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson));
    }

    @Test
    void looselyCoupledPrivacyPreservationService_NOK() throws AnonymityPalException {

        // Diabetes records
        final DBRecord dbRecord1 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord2 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord3 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord4 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        // Heart Disease
        final DBRecord dbRecord5 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord6 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord7 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_O),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord8 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord9 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord10 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_O),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        // HIV Disease
        final DBRecord dbRecord11 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_O),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecord dbRecord12 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        // Broken Arm
        final DBRecord dbRecord13 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord14 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_3),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));
        final DBRecord dbRecord15 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord16 = new DBRecord(Utils.asList(
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, SUPPRESSED_AGE_3),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_3),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        List<DBRecord> dbRecordList = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4, dbRecord5, dbRecord6,
                dbRecord7, dbRecord8, dbRecord9, dbRecord10, dbRecord11, dbRecord12, dbRecord13, dbRecord14, dbRecord15, dbRecord16);

        Set<QuasiKeyPairValue> quasiColumnsToCheck = new HashSet<>(Collections.singletonList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, SINGLE)));

        QueryResultsJson resultsJson = new QueryResultsJson(dbRecordList, quasiColumnsToCheck, false);
        Assertions.assertFalse(looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson));

        quasiColumnsToCheck = new HashSet<>(Utils.asList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new QuasiKeyPairValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB)));

        resultsJson = new QueryResultsJson(dbRecordList, quasiColumnsToCheck, false);
        Assertions.assertFalse(looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson));
    }

    @Test
    void looselyCoupledPrivacyPreservationService_many_results_OK() throws AnonymityPalException {
        final List<DBRecord> dbRecords = AnonymizedHealthSampleDataGenerator.generateData(3, 100);
        Set<QuasiKeyPairValue> quasiColumnsToCheck = new HashSet<>(Collections.singletonList(new QuasiKeyPairValue(COLUMN_NAME_MARITAL_STATUS, MARRIED)));

        QueryResultsJson resultsJson = new QueryResultsJson(dbRecords, quasiColumnsToCheck, false);
        Assertions.assertTrue(looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(resultsJson));
    }
}