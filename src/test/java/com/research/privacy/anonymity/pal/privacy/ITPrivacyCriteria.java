package com.research.privacy.anonymity.pal.privacy;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.infrastructure.repository.PrestoDbRepository;
import com.research.privacy.anonymity.pal.services.PrivacyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = Application.class)
class ITPrivacyCriteria {

    @Autowired
    PrivacyService privacyService;

    @Test
    void privacyService_isAnonymous_ok() {

    }

    @Test
    void privacyService_isNotAnonymous_ok() {

    }
}
