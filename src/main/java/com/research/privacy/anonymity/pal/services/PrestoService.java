package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.response.DBRecordKeyValue;
import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import com.research.privacy.anonymity.pal.api.response.QueryResultsResponseJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.Attribute;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.infrastructure.repository.PrestoDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.research.privacy.anonymity.pal.dataset.attributes.Attribute.getResolvedAttribute;

@Slf4j
@Service
public class PrestoService {

    private final PrestoDbRepository prestoDbRepository;

    public PrestoService(PrestoDbRepository prestoDbRepository) {
        this.prestoDbRepository = prestoDbRepository;
    }

    public QueryResultsResponseJson getQueryResultsSimple(final String query) throws AnonymityPalException {
        final List<Map<String, Object>> resultList = prestoDbRepository.findResultList(query);
        if (Utils.isNotEmpty(resultList)) {
            final List<DBRecord> dbRecords = convertResultList(resultList);
            return convertDBResultsToJson(dbRecords);
        }
        return null;
    }

    private QueryResultsResponseJson convertDBResultsToJson(final List<DBRecord> dbRecords) {
        Set<String> quasiColumns = new HashSet<>();
        Set<String> columnNames = new HashSet<>();
        List<DBRecordWrapper> dbRecordWrapper = new ArrayList<>();

        dbRecords.forEach(dbRecord -> {
            quasiColumns.addAll(dbRecord.getQIColumnsLabels());
            columnNames.addAll(dbRecord.getColumnNames());

            final List<Attribute> attributes = dbRecord.getAttributes();

            List<DBRecordKeyValue> dbRecordList = new ArrayList<>();
            attributes.forEach(a -> dbRecordList.add(new DBRecordKeyValue(a.getColumnName(), String.valueOf(a.getValue()))));

            dbRecordWrapper.add(new DBRecordWrapper(dbRecordList));
        });


        return new QueryResultsResponseJson(quasiColumns, columnNames, dbRecordWrapper);
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

    public static List<DBRecord> transformToDBRecords(List<DBRecordWrapper> dbRecordJsonList) {
        List<DBRecord> dbRecords = new ArrayList<>();

        if (Utils.isEmpty(dbRecordJsonList)) {
            return new ArrayList<>();
        }

        dbRecordJsonList.forEach(dbRecordWrapper -> {
            final List<DBRecordKeyValue> keyValues = dbRecordWrapper.getDbRecordJsonList();
            List<Attribute> attributeList = new ArrayList<>();

            keyValues.forEach(dbRecordKeyValue -> {

                final String columnName = dbRecordKeyValue.getColumnName();
                final String value = dbRecordKeyValue.getRecordValue();

                Attribute attribute = getResolvedAttribute(columnName, value);
                attributeList.add(attribute);
            });
            dbRecords.add(new DBRecord(attributeList, false));
        });
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
}
