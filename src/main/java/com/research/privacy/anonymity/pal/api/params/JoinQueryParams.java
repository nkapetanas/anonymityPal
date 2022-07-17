package com.research.privacy.anonymity.pal.api.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinQueryParams {
    private String tableToJoinPathCatalog;
    private String joinOperator;
    private List<String> joinTableColumnValues;
}
