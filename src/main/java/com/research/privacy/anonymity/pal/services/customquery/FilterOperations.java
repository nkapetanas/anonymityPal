package com.research.privacy.anonymity.pal.services.customquery;

import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilterOperations {
    private String columnName;
    private List<String> columnValues;
    private FilterOperators filterOperator;
}
