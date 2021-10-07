package com.research.privacy.anonymity.pal.dataset.attributes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

@Slf4j
@Getter
@NoArgsConstructor
public abstract class Attribute {

    private AttributeEnumType attributeType;
    private IdentifierEnumType identifierType;

    // name of column in the corresponding db schema
    private String columnName;

    private String modifiedValue; // Where the generalised value is stored

    protected Attribute(AttributeEnumType attributeType, IdentifierEnumType identifierType, String columnName) {
        this.attributeType = attributeType;
        this.identifierType = identifierType;
        this.columnName = columnName;
    }

    public static AttributeEnumType resolveAttributeEnumType(final Object value) {
        if (value.getClass() == Integer.class) {
            return AttributeEnumType.NUMERIC;
        } else if (value.getClass() == String.class) {
            return AttributeEnumType.TEXT;
        } else if (value.getClass() == Date.class) {
            return AttributeEnumType.DATE;
        }
        return AttributeEnumType.NOT_APPLICABLE;
    }

    public static IdentifierEnumType resolveIdentifierEnumType(final String columnName) {
        return IdentifierEnumType.getType(columnName);
    }
}