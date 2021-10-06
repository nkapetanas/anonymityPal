package com.research.privacy.anonymity.pal.dataset.attributes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumericAttribute extends Attribute {

    private Integer value;

    public NumericAttribute(AttributeEnumType attributeEnumType, IdentifierEnumType identifierEnumType, String columnName) {
        super(attributeEnumType, identifierEnumType, null, columnName);
    }
}
