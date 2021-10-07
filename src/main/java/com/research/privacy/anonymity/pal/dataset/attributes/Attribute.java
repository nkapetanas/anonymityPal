package com.research.privacy.anonymity.pal.dataset.attributes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

@Slf4j
@Getter
@NoArgsConstructor
public abstract class Attribute {

    private AttributeEnumType attributeEnumType;
    private IdentifierEnumType identifierEnumType;

    // name of column in the corresponding db schema
    private String columnName;

    private String modifiedValue; // Where the generalised value is stored

    protected Attribute(AttributeEnumType attributeType, IdentifierEnumType identifierType, String columnName) {
        this.attributeEnumType = attributeType;
        this.identifierEnumType = identifierType;
        this.columnName = columnName;
    }

    public abstract boolean equivalentTo(Attribute other);

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