package com.research.privacy.anonymity.pal.dataset.attributes;

import com.research.privacy.anonymity.pal.dataset.attributes.enums.AttributeEnumType;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;

public class DateAttribute extends Attribute {

    public DateAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName, null);
        setValue(value);
    }

    public DateAttribute( IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(AttributeEnumType.DATE, identifierEnumType, columnName, null);
        setValue(value);
    }

    private void setValue(final Object value) {
        this.value = String.valueOf(value);
    }

    @Override
    public boolean equivalentTo(Attribute other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return getColumnName().equals(other.getColumnName()) && getValue().equals(other.getValue());
    }
}
