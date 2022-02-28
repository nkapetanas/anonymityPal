package com.research.privacy.anonymity.pal.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public enum FilterOperators {

    EQUAL_TO("Equal to", "="),
    NOT_EQUAL_TO("Not equal to", "<>"),
    GREATER_THAN("Greater than", ">"),
    LESS_THAN("Less than", "<"),
    BETWEEN("Between", "BETWEEN"),
    GREATER_THAN_OR_EQUAL_TO("Greater than or equal to", ">="),
    LESS_THAN_OR_EQUAL_TO("Less than or equal to", "<="),
    IS_EMPTY("Is empty", "IS NULL"),
    IS_NOT_EMPTY("Is not empty", "IS NOT NULL");


    @Getter
    private String field;

    @Getter
    private String sqlOperation;

    public static List<String> getFilterOperators() {
        List<String> filterOperatorsList = new ArrayList<>();
        Arrays.stream(FilterOperators.values()).forEach(filterOperators -> filterOperatorsList.add(filterOperators.getField()));
        return filterOperatorsList;
    }
}
