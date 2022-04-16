package com.research.privacy.anonymity.pal.restservices;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.api.FilterOperationsRestService;
import com.research.privacy.anonymity.pal.api.response.FilterOperatorsResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@SpringBootTest(classes = Application.class)
class ITFilterOperatorsRestService {

    @Autowired
    FilterOperationsRestService filterOperationsRestService;

    @Test
    void privacyService_getFilterOperations_OK() {
        final ResponseEntity<List<FilterOperatorsResponseJson>> restServiceResponse = filterOperationsRestService.getFilterOperations();
        Assertions.assertEquals(HttpStatus.OK, restServiceResponse.getStatusCode());
        final List<FilterOperatorsResponseJson> filterOperations = restServiceResponse.getBody();
        Assertions.assertNotNull(filterOperations);
        Assertions.assertEquals(9, filterOperations.size());
    }
}
