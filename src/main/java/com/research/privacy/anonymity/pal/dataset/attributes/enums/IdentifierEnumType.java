package com.research.privacy.anonymity.pal.dataset.attributes.enums;

import com.facebook.presto.jdbc.internal.guava.collect.ImmutableList;

import java.util.List;


public enum IdentifierEnumType {
    IDENTIFIER("name", "lastname", "resident_registration_number", "email"),
    QUASI_IDENTIFIER("zip_code", "zipcode", "zip", "age", "sex", "gender", "nationality", "phone_number", "marital_status", "blood_type"),
    SENSITIVE("disease", "health_condition", "condition", "health_issue");

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
