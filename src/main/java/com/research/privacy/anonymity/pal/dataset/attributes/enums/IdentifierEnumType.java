package com.research.privacy.anonymity.pal.dataset.attributes.enums;

import com.facebook.presto.jdbc.internal.guava.collect.ImmutableList;

import java.util.List;


public enum IdentifierEnumType {
    IDENTIFIER("name", "lastname", "resident_registration_number"),
    QUASI_IDENTIFIER("zip_code", "age", "sex", "nationality", "phone_number"),
    SENSITIVE("health_condition");

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
