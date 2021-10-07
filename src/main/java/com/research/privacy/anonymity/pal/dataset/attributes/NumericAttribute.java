package com.research.privacy.anonymity.pal.dataset.attributes;

import lombok.Getter;

public class NumericAttribute extends Attribute {

    @Getter
    public Integer numericValue;

    public NumericAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName);
        setNumericValue(value);
    }

    private void setNumericValue(final Object numericValue) {
        this.numericValue = Integer.valueOf((String) numericValue);
    }

    @Override
    public boolean equivalentTo(Attribute other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return getColumnName().equals(other.getColumnName()) && getNumericValue().equals(((NumericAttribute) other).getNumericValue());
    }
}
