package com.research.privacy.anonymity.pal;

import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.services.PrestoDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = Application.class)
class PrestoDbConnectionTests {

    @Autowired
    PrestoDbRepository prestoDbRepository;

    @Ignore
    @Test
    void prestoDbConnection_ok() {
        List<Map<String, Object>> resultList = null;
        try {
            resultList = prestoDbRepository.findResultList("SELECT * FROM tpcds.sf10.customer");
        } catch (AnonymityPalException e) {
            Assertions.fail(e.getDetails());
        }
        Assertions.assertNotNull(resultList);
    }
}
