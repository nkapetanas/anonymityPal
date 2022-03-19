package com.research.privacy.anonymity.pal.services.customquery;

import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FilterOperations {
    private String columnName;
    private String columnValue;
    private FilterOperators filterOperator;
}
