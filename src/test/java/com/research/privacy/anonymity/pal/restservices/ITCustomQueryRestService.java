package com.research.privacy.anonymity.pal.restservices;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.PrivacyRestService;
import com.research.privacy.anonymity.pal.api.params.CustomQueryParams;
import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import com.research.privacy.anonymity.pal.api.response.QueryResultsResponseJson;
import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import com.research.privacy.anonymity.pal.common.enums.JoinOperators;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.services.PrestoService;
import com.research.privacy.anonymity.pal.services.customquery.CustomQueryBuilderService;
import com.research.privacy.anonymity.pal.services.customquery.FilterOperations;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest(classes = Application.class)
class ITCustomQueryRestService {

    private static final String VALID_TABLE_1 = "mongodb.health_data_db_1.health_data_collection_1";
    private static final String VALID_TABLE_2 = "postgresql.public.health_data_collection_2";

    @Autowired
    CustomQueryBuilderService customQueryBuilderService;

    @Autowired
    PrestoService prestoService;

    @Autowired
    PrivacyRestService queriesRestService;

    @Test
    void privacyService_getCustomQueryResults_Filter_OK() {

        List<FilterOperations> filterOperationsList = new ArrayList<>();
        filterOperationsList.add(new FilterOperations("marital_status", Collections.singletonList("Single"), FilterOperators.EQUAL_TO.name()));

        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, false, null, null, null, filterOperationsList));

        Assertions.assertEquals(HttpStatus.OK, restServiceResponse.getStatusCode());
        final QueryResultsResponseJson queryResultsResponseJson = restServiceResponse.getBody();
        Assertions.assertNotNull(queryResultsResponseJson);

        final Set<String> quasiColumns = queryResultsResponseJson.getQuasiColumns();
        final List<DBRecordWrapper> dbRecordList = queryResultsResponseJson.getDbRecordList();
        Assertions.assertTrue(Utils.isNotEmpty(quasiColumns));
        Assertions.assertTrue(Utils.isNotEmpty(dbRecordList));
        Assertions.assertEquals(51, dbRecordList.size());
    }


    @Test
    void privacyService_getCustomQueryResults_Join_OK() {
        List<FilterOperations> filterOperationsList = new ArrayList<>();
        filterOperationsList.add(new FilterOperations("nationality", Collections.singletonList("European"), FilterOperators.EQUAL_TO.name()));

        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, VALID_TABLE_2, JoinOperators.LEFT_OUTER_JOIN, Utils.asList("zip", "zipcode"), filterOperationsList));

        Assertions.assertEquals(HttpStatus.OK, restServiceResponse.getStatusCode());
        final QueryResultsResponseJson queryResultsResponseJson = restServiceResponse.getBody();
        Assertions.assertNotNull(queryResultsResponseJson);

        final Set<String> quasiColumns = queryResultsResponseJson.getQuasiColumns();
        final List<DBRecordWrapper> dbRecordList = queryResultsResponseJson.getDbRecordList();
        Assertions.assertTrue(Utils.isNotEmpty(quasiColumns));
        Assertions.assertTrue(Utils.isNotEmpty(dbRecordList));
        Assertions.assertEquals(441, dbRecordList.size());
    }

    @Test
    void privacyService_getCustomQueryResults_missing_table_NOK() {
        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, null, null, null, null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());
    }

    @Test
    void privacyService_getCustomQueryResults_Join_missing_joinInfo_NOK() {
        ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, null, JoinOperators.RIGHT_OUTER_JOIN, Utils.asList("zip", "zipcode"), null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());

        restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, VALID_TABLE_2, JoinOperators.RIGHT_OUTER_JOIN, Utils.asList("", "zipcode"), null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());
    }

    @Test
    void privacyService_getCustomQueryResults_Limit_OK() {
        List<FilterOperations> filterOperationsList = new ArrayList<>();
        filterOperationsList.add(new FilterOperations("nationality", Collections.singletonList("European"), FilterOperators.EQUAL_TO.name()));

        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true,
                VALID_TABLE_2, JoinOperators.LEFT_OUTER_JOIN, Utils.asList("zip", "zipcode"), filterOperationsList, 30, null));

        Assertions.assertEquals(HttpStatus.OK, restServiceResponse.getStatusCode());
        final QueryResultsResponseJson queryResultsResponseJson = restServiceResponse.getBody();
        Assertions.assertNotNull(queryResultsResponseJson);

        final Set<String> quasiColumns = queryResultsResponseJson.getQuasiColumns();
        final List<DBRecordWrapper> dbRecordList = queryResultsResponseJson.getDbRecordList();
        Assertions.assertTrue(Utils.isNotEmpty(quasiColumns));
        Assertions.assertTrue(Utils.isNotEmpty(dbRecordList));
        Assertions.assertEquals(30, dbRecordList.size());
    }


    private CustomQueryParams createCustomQuery(String completeTablePath, boolean isJoin, String tableToJoinPathCatalog,
                                                JoinOperators joinOperator, List<String> joinTableColumnValues, List<FilterOperations> filterOperationsList) {
        return createCustomQuery(completeTablePath, isJoin, tableToJoinPathCatalog,
                joinOperator, joinTableColumnValues, filterOperationsList, null, null);
    }

    private CustomQueryParams createCustomQuery(String completeTablePath, boolean isJoin, String tableToJoinPathCatalog,
                                                JoinOperators joinOperator, List<String> joinTableColumnValues, List<FilterOperations> filterOperationsList, Integer rowLimit,
                                                String orderBy) {
        CustomQueryParams customQueryParams = new CustomQueryParams();

        customQueryParams.setCompleteTablePath(completeTablePath);
        customQueryParams.setJoin(isJoin);
        customQueryParams.setJoinTableColumnValues(joinTableColumnValues);
        customQueryParams.setTableToJoinPathCatalog(tableToJoinPathCatalog);
        customQueryParams.setJoinOperator(Utils.isNotEmpty(joinOperator) ? joinOperator.getField() : null);
        customQueryParams.setFilterOperationsList(filterOperationsList);
        customQueryParams.setRowLimit(rowLimit);
        customQueryParams.setSortBy(orderBy);
        return customQueryParams;
    }
}
