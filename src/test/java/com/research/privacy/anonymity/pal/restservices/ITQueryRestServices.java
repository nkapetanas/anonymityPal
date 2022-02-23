package com.research.privacy.anonymity.pal.restservices;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.PrestoRestService;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.NumericAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.TextAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.infrastructure.repository.PrestoDbRepository;
import com.research.privacy.anonymity.pal.services.PrestoService;
import com.research.privacy.anonymity.pal.services.PrivacyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode.AP_E_0003;
import static com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode.AP_E_0004;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest(classes = Application.class)
class ITQueryRestServices {

    private static final String COLUMN_NAME_ZIPCODE = "zipcode";
    private static final String COLUMN_NAME_AGE = "age";
    private static final String COLUMN_NAME_SEX = "sex";
    private static final String COLUMN_NAME_NATIONALITY = "nationality";
    private static final String COLUMN_NAME_HEALTH_CONDITION = "health_condition";

    private static final String ZIP_CODE_1 = "13053";
    private static final String ZIP_CODE_2 = "77055";
    private static final String ZIP_CODE_3 = "44053";

    private static final String POSTGRES_DB_NAME = "postgresql";
    private static final String MONGO_DB_NAME = "mongodb";

    private static final String MONGO_DB_COLUMN_1 = "zip";
    private static final String MONGO_DB_COLUMN_2 = "age";
    private static final String MONGO_DB_COLUMN_3 = "marital_status";
    private static final String MONGO_DB_COLUMN_4 = "health_condition";

    private static final String MONGO_DB_HEALTH_DATA_DB_1 = "health_data_db_1";



    private static final String QUERY = "SELECT * FROM tpcds.sf10.hospital";

    private static final String MALE = "M";
    private static final String FEMALE = "F";

    private static final String AGE_1 = "20";
    private static final String AGE_2 = "30";
    private static final String AGE_3 = "25";

    private static final String NATIONALITY_1 = "Greek";
    private static final String NATIONALITY_2 = "Italian";
    private static final String NATIONALITY_3 = "German";

    private static final String HEALTH_CONDITION_1 = "Heart Disease";
    private static final String HEALTH_CONDITION_2 = "Broken Arm";
    private static final String HEALTH_CONDITION_3 = "Diabetes";
    private static final String HEALTH_CONDITION_4 = "Broken Neck";
    private static final String SHOULD_NOT_FAIL = "Should not fail";
    private static final String SHOULD_FAIL = "Should fail";

    @Autowired
    PrestoService prestoService;

//    @MockBean
//    PrestoDbRepository prestoDbRepository;

//        @Test
//        void privacyService_AnonymityFulfilled_OK() throws AnonymityPalException {
//            final DBRecord dbRecord1 = new DBRecord(Utils.asList(
//                    new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
//                    new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
//                    new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
//                    new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
//                    new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
//            ));
//
//            final DBRecord dbRecord2 = new DBRecord(Utils.asList(
//                    new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
//                    new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
//                    new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
//                    new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
//                    new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
//            ));
//
//            final DBRecord dbRecord3 = new DBRecord(Utils.asList(
//                    new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
//                    new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
//                    new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
//                    new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
//                    new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
//            ));
//
//            final DBRecord dbRecord4 = new DBRecord(Utils.asList(
//                    new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
//                    new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
//                    new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
//                    new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
//                    new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
//            ));
//
//            final List<DBRecord> dbRecords = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4);
//
//            when(prestoDbRepository.findResultList(QUERY)).thenReturn(getMockResults());
//            Assertions.assertTrue(prestoService.getQueryResultsSimple(QUERY));
//        }

    @Test
    void privacyService_getAvailableCatalogs_OK(){
        final List<String> availableDbTables = prestoService.getAvailableDBs();
        Assertions.assertEquals(7, availableDbTables.size());

        Assertions.assertTrue(availableDbTables.stream().anyMatch(POSTGRES_DB_NAME::equals));
        Assertions.assertTrue(availableDbTables.stream().anyMatch(MONGO_DB_NAME::equals));
    }

    @Test
    void privacyService_getAvailableSchemasFromDB_OK(){
         List<String> availableDbSchemas = null;
        try {
            availableDbSchemas = prestoService.getAvailableSchemasFromDB("mongodb");
        } catch (Exception e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }

        Assertions.assertEquals(5, availableDbSchemas.size());
        Assertions.assertTrue(availableDbSchemas.stream().anyMatch(MONGO_DB_HEALTH_DATA_DB_1::equals));
    }

//    @Test
//    void privacyService_getAvailableSchemasFromDB_NOK_EMPTY_SELECTED_DB(){
//        try {
//            prestoService.getAvailableSchemasFromDB("");
//            Assertions.fail(SHOULD_FAIL);
//        } catch (AnonymityPalException e) {
//            Assertions.assertEquals(AP_E_0003, e.getAnonymityPalErrorCode());
//        }
//    }

    @Test
    void privacyService_getTableColumns_OK(){
        List<String> tableColumns = null;
        try {
            tableColumns = prestoService.getColumnsFromTable("mongodb.health_data_db_1.health_data_collection_1");
        } catch (Exception e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }

        Assertions.assertEquals(4, tableColumns.size());

        Assertions.assertTrue(tableColumns.stream().anyMatch(MONGO_DB_COLUMN_1::equals));
        Assertions.assertTrue(tableColumns.stream().anyMatch(MONGO_DB_COLUMN_2::equals));
        Assertions.assertTrue(tableColumns.stream().anyMatch(MONGO_DB_COLUMN_3::equals));
        Assertions.assertTrue(tableColumns.stream().anyMatch(MONGO_DB_COLUMN_4::equals));
    }

//    @Test
//    void privacyService_getTableColumns_OK_NOK_EMPTY_SELECTED_DB(){
//        try {
//            prestoService.getColumnsFromTable("");
//            Assertions.fail(SHOULD_FAIL);
//        } catch (Exception e) {
//
//        }
//    }

    private List<Map<String, Object>> getMockResults() {
        return null;
    }
}
