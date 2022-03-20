package com.research.privacy.anonymity.pal.restservices;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import com.research.privacy.anonymity.pal.api.response.QueryResultsResponseJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.services.PrestoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

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
    private static final String VALID_QUERY = "SELECT * FROM mongodb.health_data_db_1.health_data_collection_1";
    private static final String VALID_JOIN_QUERY = "SELECT * FROM mongodb.health_data_db_1.health_data_collection_1 t1 LEFT OUTER JOIN postgresql.public.health_data_collection_2 t2 ON " +
            "t1.zip = t2.zipcode WHERE t2.nationality = 'European'";

    private static final String MONGO_DB_HEALTH_DATA_DB_1 = "health_data_db_1";
    private static final String SHOULD_NOT_FAIL = "Should not fail";
    private static final String SHOULD_FAIL = "Should fail";

    @Autowired
    PrestoService prestoService;

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
    void privacyService_getQueryResultsSimple_OK() {
         QueryResultsResponseJson queryResults = null;
        try {
            queryResults = prestoService.getQueryResultsSimple(VALID_QUERY);
        } catch (AnonymityPalException e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }

        Assertions.assertNotNull(queryResults);
        final Set<String> quasiColumns = queryResults.getQuasiColumns();
        final List<DBRecordWrapper> dbRecordList = queryResults.getDbRecordList();
        Assertions.assertTrue(Utils.isNotEmpty(quasiColumns));
        Assertions.assertTrue(Utils.isNotEmpty(dbRecordList));
        Assertions.assertEquals(17, dbRecordList.size());

        Assertions.assertTrue(quasiColumns.stream().anyMatch(QUASI_COLUMN_1::equals));
        Assertions.assertTrue(quasiColumns.stream().anyMatch(QUASI_COLUMN_2::equals));
        Assertions.assertTrue(quasiColumns.stream().anyMatch(QUASI_COLUMN_3::equals));
    }

    @Test
    void privacyService_getQueryResultsSimple_Join_OK() {
         QueryResultsResponseJson queryResults = null;
        try {
            queryResults = prestoService.getQueryResultsSimple(VALID_JOIN_QUERY);
        } catch (AnonymityPalException e) {
            Assertions.fail(SHOULD_NOT_FAIL);
        }

        Assertions.assertNotNull(queryResults);
        final Set<String> quasiColumns = queryResults.getQuasiColumns();
        final List<DBRecordWrapper> dbRecordList = queryResults.getDbRecordList();
        Assertions.assertTrue(Utils.isNotEmpty(quasiColumns));
        Assertions.assertTrue(Utils.isNotEmpty(dbRecordList));
        Assertions.assertEquals(40, dbRecordList.size());
    }
}
