package com.research.privacy.anonymity.pal.common.enums;

import com.research.privacy.anonymity.pal.common.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public enum JoinOperators {

    LEFT_OUTER_JOIN("Left outer join", "LEFT OUTER JOIN %s %s ON "),
    RIGHT_OUTER_JOIN("Right outer join", "RIGHT OUTER JOIN %s %s ON "),
    INNER_JOIN("Inner join", "INNER JOIN %s %s ON ");

    @Getter
    private final String field;

    @Getter
    private final String sqlOperation;

    public static List<String> getJoinOperators() {
        List<String> joinOperators = new ArrayList<>();
        Arrays.stream(JoinOperators.values()).forEach(filterOperators -> joinOperators.add(filterOperators.getField()));
        return joinOperators;
    }

    public static JoinOperators from(String joinOperation) {
        return Utils.isEmpty(joinOperation) ? null : Arrays.stream(values()).filter((filterOperator -> filterOperator.getField().equals(joinOperation.trim()))).findFirst().orElse(null);
    }
}
