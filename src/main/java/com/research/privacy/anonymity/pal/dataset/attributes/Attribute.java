package com.research.privacy.anonymity.pal.dataset.attributes;

import com.research.privacy.anonymity.pal.dataset.attributes.enums.AttributeEnumType;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;
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

    private String generalisedValue;

    protected Object value;

    protected Attribute(AttributeEnumType attributeType, IdentifierEnumType identifierType, String columnName, Object value) {
        this.attributeEnumType = attributeType;
        this.identifierEnumType = identifierType;
        this.columnName = columnName;
        this.value = value;
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