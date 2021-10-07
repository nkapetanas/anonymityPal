package com.research.privacy.anonymity.pal.dataset.attributes;


public class TextAttribute extends Attribute {

    public String textValue;

    public TextAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName, Object value) {
        super(attributeEnumType, identifierEnumType, columnName);
        setTextValue(value);
    }

    private void setTextValue(final Object textValue) {
        this.textValue = String.valueOf(textValue);
    }
}
