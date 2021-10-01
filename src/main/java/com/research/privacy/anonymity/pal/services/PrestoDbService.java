package com.research.privacy.anonymity.pal.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PrestoDbService {

    private final JdbcTemplate prestoTemplate;

    public PrestoDbService(@Qualifier("prestoTemplate") JdbcTemplate prestoTemplate) {
        this.prestoTemplate = prestoTemplate;
    }

    public List<Map<String, Object>> findResultList(final String query) {
        return prestoTemplate.queryForList(query);
    }
}
