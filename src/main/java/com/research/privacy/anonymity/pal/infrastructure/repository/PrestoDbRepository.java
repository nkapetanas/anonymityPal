package com.research.privacy.anonymity.pal.infrastructure.repository;

import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class PrestoDbRepository {

    private static final String SHOW_CATALOGS_QUERY = "SHOW CATALOGS";
    private static final String SHOW_SCHEMAS_FROM_DB_QUERY = "SHOW SCHEMAS FROM %s";
    private static final String SHOW_COLUMNS_FROM_TABLE_QUERY = "SHOW COLUMNS FROM %s";
    private static final String SHOW_TABLES_FROM_SCHEMA_QUERY = "SHOW TABLES FROM %s";

    private final Jdbi jdbi;

    public PrestoDbRepository(@Qualifier("jdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Map<String, Object>> findResultList(final String query) throws AnonymityPalException {
        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(query)
                            .mapToMap()
                            .list());
        } catch (Exception e) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0001);
        }
    }

    public List<String> findAvailableCatalogs() {
        return jdbi.withHandle(handle ->
                handle.createQuery(SHOW_CATALOGS_QUERY)
                        .mapTo(String.class)
                        .list());
    }

    public List<String> getAvailableSchemasFromDB(final String selectedDB) {
        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(String.format(SHOW_SCHEMAS_FROM_DB_QUERY, selectedDB))
                            .mapTo(String.class)
                            .list());
        } catch (Exception e) {
            log.warn("Exception occurred when retrieving the available schemas from db", e);
            return Collections.emptyList();
        }
    }

    public List<String> getTablesFromSchema(final String schema) {
        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(String.format(SHOW_TABLES_FROM_SCHEMA_QUERY, schema))
                            .mapTo(String.class)
                            .list());
        } catch (Exception e) {
            log.warn("Exception occurred when retrieving the tables from the schema", e);
            return Collections.emptyList();
        }
    }

    public List<String> getColumnsFromTable(final String table) {
        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(String.format(SHOW_COLUMNS_FROM_TABLE_QUERY, table))
                            .mapTo(String.class)
                            .list());
        } catch (Exception e) {
            log.warn("Exception occurred when retrieving the columns from the table", e);
            return Collections.emptyList();
        }
    }
}
