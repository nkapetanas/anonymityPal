package com.research.privacy.anonymity.pal.infrastructure.repository;

import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class PrestoDbRepository {

    private static final String SHOW_CATALOGS_QUERY = "SHOW CATALOGS";
    private static final String SHOW_SCHEMAS_FROM_DB_QUERY = "SHOW SCHEMAS FROM %s";
    private static final String SHOW_COLUMNS_FROM_TABLE_QUERY = "SHOW COLUMNS FROM %s";
    private static final String SHOW_TABLES_FROM_SCHEMA_QUERY = "SHOW TABLES FROM %s";

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

    public List<String> findAvailableCatalogs() {
        final List<Map<String, Object>> catalogs = prestoTemplate.queryForList(SHOW_CATALOGS_QUERY);
        List<String> dbNames = new ArrayList<>();
        for (Map<String, Object> map : catalogs) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                dbNames.add(String.valueOf(entry.getValue()));
            }
        }
        return dbNames;
    }

    public List<String> getAvailableSchemasFromDB(final String selectedDB) {
        final List<Map<String, Object>> schemas = prestoTemplate.queryForList(String.format(SHOW_SCHEMAS_FROM_DB_QUERY, selectedDB));
        List<String> schemasList = new ArrayList<>();
        for (Map<String, Object> map : schemas) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                schemasList.add(String.valueOf(entry.getValue()));
            }
        }
        return schemasList;
    }

    public List<String> getTablesFromSchema(final String schema) {
        final List<Map<String, Object>> tables = prestoTemplate.queryForList(String.format(SHOW_TABLES_FROM_SCHEMA_QUERY, schema));
        List<String> dbTables = new ArrayList<>();
        for (Map<String, Object> map : tables) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                dbTables.add(String.valueOf(entry.getValue()));
            }
        }
        return dbTables;
    }

    public List<String> getColumnsFromTable(final String table) {
        final List<Map<String, Object>> columns = prestoTemplate.queryForList(String.format(SHOW_COLUMNS_FROM_TABLE_QUERY, table));
        List<String> tableColumns = new ArrayList<>();
        for (Map<String, Object> map : columns) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if("Column".equals(entry.getKey())){
                    tableColumns.add(String.valueOf(entry.getValue()));
                }
            }
        }
        return tableColumns;
    }
}
