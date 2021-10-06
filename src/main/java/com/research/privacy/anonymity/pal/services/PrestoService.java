package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.ResultsJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.infrastructure.repository.PrestoDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class PrestoService {

    private final PrestoDbRepository prestoDbRepository;

    public PrestoService(PrestoDbRepository prestoDbRepository) {
        this.prestoDbRepository = prestoDbRepository;
    }

    public List<ResultsJson> getQueryResults(final String query) throws AnonymityPalException {
        final List<Map<String, Object>> resultList = prestoDbRepository.findResultList(query);

        convertResultList(resultList);
        return null;
    }

    private List<DBRecord> convertResultList(List<Map<String, Object>> resultList) {
        List<DBRecord> dbRecords = new ArrayList<>();

       if(Utils.isEmpty(resultList)){
            return new ArrayList<>();
       }

        for (Map<String, Object> map : resultList) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
            }
        }
        return dbRecords;
    }
}
