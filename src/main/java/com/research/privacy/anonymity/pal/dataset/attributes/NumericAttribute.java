package com.research.privacy.anonymity.pal.dataset.attributes;

public class NumericAttribute extends Attribute {
    public Integer numericValue;

    public NumericAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName);
        setNumericValue(value);
    }

    private void setNumericValue(final Object numericValue) {
        this.numericValue = Integer.valueOf((String) numericValue);
    }
}
