package com.research.privacy.anonymity.pal;

import com.research.privacy.anonymity.pal.services.PrestoDbRepository;
import lombok.extern.slf4j.Slf4j;
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

	@Test
	void prestoDbConnection_ok() {
		final List<Map<String, Object>> resultList = prestoDbRepository.findResultList("SELECT * FROM tpcds.sf10.customer");
		Assertions.assertNotNull(resultList);
	}

}
