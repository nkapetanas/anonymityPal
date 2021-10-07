package com.research.privacy.anonymity.pal.dataset.attributes;

import com.facebook.presto.jdbc.internal.guava.collect.ImmutableList;

import java.util.List;


public enum IdentifierEnumType {
    IDENTIFIER("columnName1", "columnName2", "columnName3"),
    QUASI_IDENTIFIER("columnName4", "columnName5", "columnName6"),
    SENSITIVE("columnName7", "columnName8", "columnName9");

    private final List<String> dbColumnNames;

    public List<String> getDbColumnNames() {
        return dbColumnNames;
    }

    IdentifierEnumType(String... dbColumnNames) {
        this.dbColumnNames = ImmutableList.copyOf(dbColumnNames);
    }

    public static IdentifierEnumType getType(final String dbColumnName) {
        for (IdentifierEnumType type : values()) {
            final List<String> dbColumnNames = type.getDbColumnNames();
            if (dbColumnNames.contains(dbColumnName)) {
                return type;
            }
        }
        return null;
    }
}
