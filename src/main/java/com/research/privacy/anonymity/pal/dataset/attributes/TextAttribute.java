package com.research.privacy.anonymity.pal.dataset.attributes;

public class TextAttribute extends Attribute {

    private String textValue;

    public TextAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName) {
        super(attributeEnumType, identifierEnumType, null, columnName);
    }
}
