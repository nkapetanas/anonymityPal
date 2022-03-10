package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.ResultsJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.Attribute;
import com.research.privacy.anonymity.pal.dataset.attributes.DateAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.NumericAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.TextAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.AttributeEnumType;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.infrastructure.repository.PrestoDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class PrestoService {

    private final PrestoDbRepository prestoDbRepository;
    private final PrivacyService privacyService;


    public PrestoService(PrestoDbRepository prestoDbRepository, PrivacyService privacyService) {
        this.prestoDbRepository = prestoDbRepository;
        this.privacyService = privacyService;
    }

    public List<DBRecord> getQueryResultsPrivacyChecked(final String query) throws AnonymityPalException {
        final List<Map<String, Object>> resultList = prestoDbRepository.findResultList(query);

        List<DBRecord> dbRecords = convertResultList(resultList);

        if (!privacyService.isPrivacyModelFulfilled(dbRecords)) {
            // TODO is it needed?
//            dbRecords = privacyService.anonymize(dbRecords);
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0002);
        }
        return dbRecords;
    }

    public ResultsJson getQueryResultsSimple(final String query) throws AnonymityPalException {
        final List<Map<String, Object>> resultList = prestoDbRepository.findResultList(query);
        if (Utils.isNotEmpty(resultList)) {
            final List<DBRecord> dbRecords = convertResultList(resultList);
            return convertDBResultsToJson(dbRecords);
        }
        return null;
    }

    private ResultsJson convertDBResultsToJson(final List<DBRecord> dbRecords) {
        ResultsJson resultsJson = new ResultsJson();
        dbRecords.forEach(dbRecord -> {

        });
        return resultsJson;
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
            dbRecords.add(new DBRecord(attributeList, false));
        }
        return dbRecords;
    }

    public List<String> getAvailableDBs() {
        return prestoDbRepository.findAvailableCatalogs();
    }

    public List<String> getAvailableSchemasFromDB(final String selectedDB) {
        return prestoDbRepository.getAvailableSchemasFromDB(selectedDB);
    }

    public List<String> getTablesFromSchema(final String schema) {
        return prestoDbRepository.getTablesFromSchema(schema);
    }

    public List<String> getColumnsFromTable(final String table) {
        return prestoDbRepository.getColumnsFromTable(table);
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
