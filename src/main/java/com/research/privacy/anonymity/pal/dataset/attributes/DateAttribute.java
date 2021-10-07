package com.research.privacy.anonymity.pal.dataset.attributes;

import lombok.Getter;

public class DateAttribute extends Attribute {

    @Getter
    public String dateValue;

    public DateAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName);
        setValue(dateValue);
    }

    private void setValue(final Object value) {
        this.dateValue = String.valueOf(value);
    }

    @Override
    public boolean equivalentTo(Attribute other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return getColumnName().equals(other.getColumnName()) && getDateValue().equals(((DateAttribute) other).getDateValue());
    }
}
