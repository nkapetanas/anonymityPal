package com.research.privacy.anonymity.pal.dataset.attributes;


import com.research.privacy.anonymity.pal.dataset.attributes.enums.AttributeEnumType;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;

public class TextAttribute extends Attribute {

    public TextAttribute(IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(AttributeEnumType.TEXT, identifierEnumType, columnName, null);
        setTextValue(value);
    }

    public TextAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName, null);
        setTextValue(value);
    }

    private void setTextValue(final Object value) {
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
