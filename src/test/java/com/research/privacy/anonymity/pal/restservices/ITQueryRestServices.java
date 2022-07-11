package com.research.privacy.anonymity.pal.restservices;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.params.PrivacyCheckParams;
import com.research.privacy.anonymity.pal.api.response.DBRecordKeyValue;
import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.services.PrestoService;
import com.research.privacy.anonymity.pal.services.PrivacyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = Application.class)
class ITQueryRestServices {

    private static final String POSTGRES_DB_NAME = "postgresql";
    private static final String MONGO_DB_NAME = "mongodb";

    private static final String MONGO_DB_COLUMN_1 = "zip";
    private static final String MONGO_DB_COLUMN_2 = "age";
    private static final String MONGO_DB_COLUMN_3 = "marital_status";
    private static final String MONGO_DB_COLUMN_4 = "health_condition";

    private static final String QUASI_COLUMN_1 = "zip";
    private static final String QUASI_COLUMN_2 = "marital_status";
    private static final String QUASI_COLUMN_3 = "age";

    private static final String UNKNOWN_TABLE = "mongodb.health_data_db_1.unknown";
    private static final String VALID_TABLE = "mongodb.health_data_db_1.health_data_collection_1";


    private static final String MONGO_DB_HEALTH_DATA_DB_1 = "health_data_db_1";
    private static final String SHOULD_NOT_FAIL = "Should not fail";
    private static final int K_ANONYMITY_PARAM = 2;
    private static final int L_DIVERSITY_PARAM_2 = 2;
    private static final int L_DIVERSITY_PARAM_3 = 3;

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
    private static final String HEALTH_CONDITION_4 = "Broken_Arm";

    @Autowired
    PrestoService prestoService;

    @Autowired
    PrivacyService privacyService;

    @Test
    void privacyService_getAvailableCatalogs_OK() {
        final List<String> availableDbTables = prestoService.getAvailableDBs();
        Assertions.assertEquals(7, availableDbTables.size());

        Assertions.assertTrue(availableDbTables.stream().anyMatch(POSTGRES_DB_NAME::equals));
        Assertions.assertTrue(availableDbTables.stream().anyMatch(MONGO_DB_NAME::equals));
    }

    @Test
    void privacyService_getAvailableSchemasFromDB_OK() {
        List<String> availableDbSchemas = null;
        try {
            availableDbSchemas = prestoService.getAvailableSchemasFromDB("mongodb");
        } catch (Exception e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }

        Assertions.assertEquals(5, availableDbSchemas.size());
        Assertions.assertTrue(availableDbSchemas.stream().anyMatch(MONGO_DB_HEALTH_DATA_DB_1::equals));
    }

    @Test
    void privacyService_getAvailableSchemasFromDB_NOK_EMPTY_SELECTED_DB() {
        final List<String> availableSchemasFromDB = prestoService.getAvailableSchemasFromDB("");
        Assertions.assertTrue(availableSchemasFromDB.isEmpty());
    }

    @Test
    void privacyService_getAvailableSchemasFromDB_NOK_NOT_EXISTING_SELECTED_DB() {
        final List<String> availableSchemasFromDB = prestoService.getAvailableSchemasFromDB("drcfvg");
        Assertions.assertTrue(availableSchemasFromDB.isEmpty());
    }

    @Test
    void privacyService_getTableColumns_OK() {
        List<String> tableColumns = null;
        try {
            tableColumns = prestoService.getColumnsFromTable(VALID_TABLE);
        } catch (Exception e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }

        Assertions.assertEquals(4, tableColumns.size());

        Assertions.assertTrue(tableColumns.stream().anyMatch(MONGO_DB_COLUMN_1::equals));
        Assertions.assertTrue(tableColumns.stream().anyMatch(MONGO_DB_COLUMN_2::equals));
        Assertions.assertTrue(tableColumns.stream().anyMatch(MONGO_DB_COLUMN_3::equals));
        Assertions.assertTrue(tableColumns.stream().anyMatch(MONGO_DB_COLUMN_4::equals));
    }

    @Test
    void privacyService_getTableColumns_OK_NOK_EMPTY_SELECTED_DB() {
        final List<String> columnsFromTable = prestoService.getColumnsFromTable("");
        Assertions.assertTrue(columnsFromTable.isEmpty());
    }

    @Test
    void privacyService_getTableColumns_OK_NOK_NOT_EXISTING_SELECTED_DB() {
        final List<String> columnsFromTable = prestoService.getColumnsFromTable(UNKNOWN_TABLE);
        Assertions.assertTrue(columnsFromTable.isEmpty());
    }

    @Test
    void privacyService_getQueryResultsPrivacyChecked_OK() {
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
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecordWrapper dbRecord4 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_1),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        PrivacyCheckParams privacyCheckParams = new PrivacyCheckParams();
        privacyCheckParams.setKAnonymityParam(K_ANONYMITY_PARAM);
        privacyCheckParams.setLDiversityParam(L_DIVERSITY_PARAM_2);
        privacyCheckParams.setDbRecordList(Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4));

        try {
            Assertions.assertTrue(privacyService.getQueryResultsPrivacyChecked(privacyCheckParams));
        } catch (AnonymityPalException e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }
    }

    @Test
    void privacyService_getQueryResultsPrivacyChecked_NOK() {
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
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_B),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));


        PrivacyCheckParams privacyCheckParams = new PrivacyCheckParams();
        privacyCheckParams.setKAnonymityParam(K_ANONYMITY_PARAM);
        privacyCheckParams.setLDiversityParam(L_DIVERSITY_PARAM_2);
        privacyCheckParams.setDbRecordList(Utils.asList(dbRecord1, dbRecord2, dbRecord3));

        try {
            Assertions.assertFalse(privacyService.getQueryResultsPrivacyChecked(privacyCheckParams));
        } catch (AnonymityPalException e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }
    }

    @Test
    void privacyService_getQueryResultsPrivacyChecked_OK_2() {
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
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, SINGLE),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_1),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_A),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecordWrapper dbRecord4 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecordWrapper dbRecord5 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        PrivacyCheckParams privacyCheckParams = new PrivacyCheckParams();
        privacyCheckParams.setKAnonymityParam(K_ANONYMITY_PARAM);
        privacyCheckParams.setLDiversityParam(L_DIVERSITY_PARAM_3);
        privacyCheckParams.setDbRecordList(Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4, dbRecord5));

        try {
            Assertions.assertFalse(privacyService.getQueryResultsPrivacyChecked(privacyCheckParams));
        } catch (AnonymityPalException e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }

        final DBRecordWrapper dbRecord6 = new DBRecordWrapper(Utils.asList(
                new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, SUPPRESSED_ZIP_CODE_2),
                new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, MARRIED),
                new DBRecordKeyValue(COLUMN_NAME_AGE, SUPPRESSED_AGE_2),
                new DBRecordKeyValue(COLUMN_NAME_GENDER, MALE),
                new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, BLOOD_TYPE_AB),
                new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        privacyCheckParams.setDbRecordList(Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4, dbRecord5, dbRecord6));

        try {
            Assertions.assertTrue(privacyService.getQueryResultsPrivacyChecked(privacyCheckParams));
        } catch (AnonymityPalException e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }
    }
}
