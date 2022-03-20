package com.research.privacy.anonymity.pal.restservices;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.QueriesRestService;
import com.research.privacy.anonymity.pal.api.params.CustomQueryParams;
import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import com.research.privacy.anonymity.pal.api.response.QueryResultsResponseJson;
import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.services.PrestoService;
import com.research.privacy.anonymity.pal.services.customquery.CustomQueryBuilderService;
import com.research.privacy.anonymity.pal.services.customquery.FilterOperations;
import com.research.privacy.anonymity.pal.services.customquery.JoinOperation;
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
        filterOperationsList.add(new FilterOperations("marital_status", Collections.singletonList("single"), FilterOperators.EQUAL_TO));

        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, false, null, filterOperationsList));

        Assertions.assertEquals(HttpStatus.OK, restServiceResponse.getStatusCode());
        final QueryResultsResponseJson queryResultsResponseJson = restServiceResponse.getBody();
        Assertions.assertNotNull(queryResultsResponseJson);

        final Set<String> quasiColumns = queryResultsResponseJson.getQuasiColumns();
        final List<DBRecordWrapper> dbRecordList = queryResultsResponseJson.getDbRecordList();
        Assertions.assertTrue(Utils.isNotEmpty(quasiColumns));
        Assertions.assertTrue(Utils.isNotEmpty(dbRecordList));
        Assertions.assertEquals(9, dbRecordList.size());
    }


    @Test
    void privacyService_getCustomQueryResults_Join_OK() {
        List<FilterOperations> filterOperationsList = new ArrayList<>();
        filterOperationsList.add(new FilterOperations("nationality", Collections.singletonList("European"), FilterOperators.EQUAL_TO));

        JoinOperation joinOperation = new JoinOperation(VALID_TABLE_2, Utils.asList("zip", "zipcode"));

        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, joinOperation, filterOperationsList));

        Assertions.assertEquals(HttpStatus.OK, restServiceResponse.getStatusCode());
        final QueryResultsResponseJson queryResultsResponseJson = restServiceResponse.getBody();
        Assertions.assertNotNull(queryResultsResponseJson);

        final Set<String> quasiColumns = queryResultsResponseJson.getQuasiColumns();
        final List<DBRecordWrapper> dbRecordList = queryResultsResponseJson.getDbRecordList();
        Assertions.assertTrue(Utils.isNotEmpty(quasiColumns));
        Assertions.assertTrue(Utils.isNotEmpty(dbRecordList));
        Assertions.assertEquals(40, dbRecordList.size());
    }

    @Test
    void privacyService_getCustomQueryResults_missing_table_NOK() {
        final ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, null, null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());
    }

    @Test
    void privacyService_getCustomQueryResults_Join_missing_joinInfo_NOK() {
        ResponseEntity<QueryResultsResponseJson> restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, null, null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());

        JoinOperation joinOperation = new JoinOperation(null, Utils.asList("zip", "zipcode"));

        restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, joinOperation, null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());

        joinOperation = new JoinOperation(VALID_TABLE_2, Utils.asList("", "zipcode"));

        restServiceResponse = queriesRestService.getCustomQueryResults(createCustomQuery(VALID_TABLE_1, true, joinOperation, null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restServiceResponse.getStatusCode());
    }

    private CustomQueryParams createCustomQuery(String completeTablePath, boolean isJoin, JoinOperation joinOperation, List<FilterOperations> filterOperationsList) {
        CustomQueryParams customQueryParams = new CustomQueryParams();

        customQueryParams.setCompleteTablePath(completeTablePath);
        customQueryParams.setJoin(isJoin);
        customQueryParams.setJoinOperation(joinOperation);
        customQueryParams.setFilterOperationsList(filterOperationsList);
        return customQueryParams;
    }
}
