package com.research.privacy.anonymity.pal.dataset.attributes;

import com.research.privacy.anonymity.pal.dataset.attributes.enums.AttributeEnumType;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;

public class NumericAttribute extends Attribute {

    public NumericAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName, null);
        setNumericValue(value);
    }

    public NumericAttribute(IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(AttributeEnumType.NUMERIC, identifierEnumType, columnName, null);
        setNumericValue(value);
    }

    private void setNumericValue(final Object numericValue) {
        final String numericValueToString = String.valueOf(numericValue);
        this.value = Integer.valueOf(numericValueToString);
    }

    @Override
    public boolean equivalentTo(Attribute other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return getColumnName().equals(other.getColumnName()) && getValue().equals(other.getValue());
    }
}
