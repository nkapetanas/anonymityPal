package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.ResultsJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.*;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.infrastructure.repository.PrestoDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PrestoService {

    private final PrestoDbRepository prestoDbRepository;
    private final PrivacyService privacyService;


    public PrestoService(PrestoDbRepository prestoDbRepository, PrivacyService privacyService) {
        this.prestoDbRepository = prestoDbRepository;
        this.privacyService = privacyService;
    }

    public List<ResultsJson> getQueryResults(final String query) throws AnonymityPalException {
        final List<Map<String, Object>> resultList = prestoDbRepository.findResultList(query);

        List<DBRecord> dbRecords = convertResultList(resultList);

        if(!privacyService.isPrivacyModelFulfilled(dbRecords)){
            dbRecords = privacyService.anonymize(dbRecords);
        }
        return convertAnonymizedDBResultsToJson(dbRecords);
    }

    private List<ResultsJson> convertAnonymizedDBResultsToJson(final List<DBRecord> dbRecords) {
        return null;
    }

    private List<DBRecord> convertResultList(List<Map<String, Object>> resultList) {
        List<DBRecord> dbRecords = new ArrayList<>();

        if (Utils.isEmpty(resultList)) {
            return new ArrayList<>();
        }

        for (Map<String, Object> map : resultList) {
            List<Attribute> attributeList = new ArrayList<>();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                Attribute attribute = getResolvedAttribute(key, value);
                attributeList.add(attribute);
            }
            dbRecords.add(new DBRecord(attributeList));
        }
        return dbRecords;
    }

    private Attribute getResolvedAttribute(final String key, final Object value) {
        final AttributeEnumType attributeEnumType = Attribute.resolveAttributeEnumType(value);
        final IdentifierEnumType identifierEnumType = Attribute.resolveIdentifierEnumType(key);

        if (AttributeEnumType.TEXT.equals(attributeEnumType)) {
            return new TextAttribute(attributeEnumType, identifierEnumType, key, value);

        } else if (AttributeEnumType.NUMERIC.equals(attributeEnumType)) {
            return new NumericAttribute(attributeEnumType, identifierEnumType, key, value);

        } else if (AttributeEnumType.DATE.equals(attributeEnumType)) {
            return new DateAttribute(attributeEnumType, identifierEnumType, key, value);
        }
        return null;
    }
}
