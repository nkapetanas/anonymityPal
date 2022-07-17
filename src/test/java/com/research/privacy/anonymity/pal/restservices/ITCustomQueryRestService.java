package com.research.privacy.anonymity.pal.restservices;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.QueriesRestService;
import com.research.privacy.anonymity.pal.api.params.CustomQueryParams;
import com.research.privacy.anonymity.pal.api.params.JoinQueryParams;
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
    QueriesRestService queriesRestService;

    @Test
    void privacyService_getCustomQueryResults_Filter_OK() {

        List<FilterOperations> filterOperationsList = new ArrayList<>();
        filterOperationsList.add(new FilterOperations(VALID_TABLE_1, "marital_status", Collections.singletonList("Single"), FilterOperators.EQUAL_TO.name()));

        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(
                createCustomQuery(VALID_TABLE_1, null, filterOperationsList, null, null));

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
        filterOperationsList.add(new FilterOperations(VALID_TABLE_2, "nationality", Collections.singletonList("European"), FilterOperators.EQUAL_TO.name()));
        final List<JoinQueryParams> joinQueryParams = Utils.asList(new JoinQueryParams(VALID_TABLE_2, JoinOperators.LEFT_OUTER_JOIN.getField(), Utils.asList("zip", "zipcode")));

        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(
                createCustomQuery(VALID_TABLE_1, joinQueryParams, filterOperationsList));

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
    void privacyService_getCustomQueryResults_Join_Multiple_Tables_OK() {
        List<FilterOperations> filterOperationsList = new ArrayList<>();
        filterOperationsList.add(new FilterOperations(VALID_TABLE_2, "gender", Collections.singletonList("M"), FilterOperators.EQUAL_TO.name()));

        final List<JoinQueryParams> joinQueryParams = Utils.asList(new JoinQueryParams(VALID_TABLE_2, JoinOperators.LEFT_OUTER_JOIN.getField(), Utils.asList("zip", "zipcode")),
                new JoinQueryParams(VALID_TABLE_2, JoinOperators.RIGHT_OUTER_JOIN.getField(), Utils.asList("health_condition", "healthcondition")));

        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(
                createCustomQuery(VALID_TABLE_1, joinQueryParams, filterOperationsList));

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
    void privacyService_getCustomQueryResults_Join_missing_joinInfo_NOK() {
        List<JoinQueryParams> joinQueryParams = Utils.asList(new JoinQueryParams(null, JoinOperators.LEFT_OUTER_JOIN.getField(), Utils.asList("zip", "zipcode")));

        ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, joinQueryParams, null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());

        joinQueryParams = Utils.asList(new JoinQueryParams(VALID_TABLE_2, JoinOperators.LEFT_OUTER_JOIN.getField(), Utils.asList(" ", "zipcode")));
        restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, joinQueryParams, null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());
    }

    @Test
    void privacyService_getCustomQueryResults_Limit_OK() {
        List<FilterOperations> filterOperationsList = new ArrayList<>();
        filterOperationsList.add(new FilterOperations(VALID_TABLE_2, "nationality", Collections.singletonList("European"), FilterOperators.EQUAL_TO.name()));

        final List<JoinQueryParams> joinQueryParams = Utils.asList(new JoinQueryParams(VALID_TABLE_2, JoinOperators.LEFT_OUTER_JOIN.getField(), Utils.asList("zip", "zipcode")));
        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1,
                joinQueryParams, filterOperationsList, 30, null));

        Assertions.assertEquals(HttpStatus.OK, restServiceResponse.getStatusCode());
        final QueryResultsResponseJson queryResultsResponseJson = restServiceResponse.getBody();
        Assertions.assertNotNull(queryResultsResponseJson);

        final Set<String> quasiColumns = queryResultsResponseJson.getQuasiColumns();
        final List<DBRecordWrapper> dbRecordList = queryResultsResponseJson.getDbRecordList();
        Assertions.assertTrue(Utils.isNotEmpty(quasiColumns));
        Assertions.assertTrue(Utils.isNotEmpty(dbRecordList));
        Assertions.assertEquals(30, dbRecordList.size());
    }


    private CustomQueryParams createCustomQuery(String completeTablePath, List<JoinQueryParams> joinQueryParams, List<FilterOperations> filterOperationsList) {
        return createCustomQuery(completeTablePath, joinQueryParams, filterOperationsList, null, null);
    }

    private CustomQueryParams createCustomQuery(String completeTablePath, List<JoinQueryParams> joinQueryParams, List<FilterOperations> filterOperationsList, Integer rowLimit,
                                                String orderBy) {
        CustomQueryParams customQueryParams = new CustomQueryParams();

        customQueryParams.setCompleteTablePath(completeTablePath);
        customQueryParams.setJoinQueryParams(joinQueryParams);
        customQueryParams.setFilterOperationsList(filterOperationsList);
        customQueryParams.setRowLimit(rowLimit);
        customQueryParams.setSortBy(orderBy);
        return customQueryParams;
    }
}
