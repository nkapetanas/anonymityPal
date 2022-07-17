package com.research.privacy.anonymity.pal.services.customquery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilterOperations {
    private String completeTablePathToFilter;
    private String columnName;
    private List<String> columnValues;
    private String filterOperator;
}
