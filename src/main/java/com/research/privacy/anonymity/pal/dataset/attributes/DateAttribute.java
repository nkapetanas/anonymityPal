package com.research.privacy.anonymity.pal.dataset.attributes;

public class DateAttribute extends Attribute {

    private String dateValue;

    public DateAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName) {
        super(attributeEnumType, identifierEnumType, null, columnName);
    }
}
