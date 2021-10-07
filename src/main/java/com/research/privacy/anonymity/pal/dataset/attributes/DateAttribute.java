package com.research.privacy.anonymity.pal.dataset.attributes;

public class DateAttribute extends Attribute {

    public String dateValue;

    public DateAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName);
    }

    private void setValue(final Object value) {
        this.dateValue = String.valueOf(value);
    }
}
