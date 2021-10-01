package com.research.privacy.anonymity.pal;

import com.research.privacy.anonymity.pal.services.PrestoDbService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = Application.class)
class ApplicationTests {

	@Autowired
	PrestoDbService prestoDbService;

	@Test
	void prestoDbConnection_ok() {

	}

}
