package com.research.privacy.anonymity.pal.dataset.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Attribute {

    private AttributeEnumType attributeType;
    private IdentifierEnumType identifierType;
    private String modifiedValue; // Where the generalised value is stored

    // name of column in the corresponding db schema
    private String columnName;
}