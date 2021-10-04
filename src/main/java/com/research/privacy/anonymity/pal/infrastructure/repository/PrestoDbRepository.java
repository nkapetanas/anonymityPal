package com.research.privacy.anonymity.pal.infrastructure.repository;

import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class PrestoDbRepository {

    private final JdbcTemplate prestoTemplate;

    public PrestoDbRepository(@Qualifier("prestoTemplate") JdbcTemplate prestoTemplate) {
        this.prestoTemplate = prestoTemplate;
    }

    public List<Map<String, Object>> findResultList(final String query) throws AnonymityPalException {
        try {
            return prestoTemplate.queryForList(query);
        } catch (Exception e) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0001);
        }
    }
}
