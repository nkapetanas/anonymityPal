package com.research.privacy.anonymity.pal.dataset.attributes;


import lombok.Getter;

public class TextAttribute extends Attribute {
    @Getter
    public String textValue;

    public TextAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName);
        setTextValue(value);
    }

    private void setTextValue(final Object textValue) {
        this.textValue = String.valueOf(textValue);
    }

    @Override
    public boolean equivalentTo(Attribute other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return getColumnName().equals(other.getColumnName()) && getTextValue().equals(((TextAttribute) other).getTextValue());
    }
}
