package com.research.privacy.anonymity.pal.api.params;

import com.research.privacy.anonymity.pal.services.customquery.FilterOperations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomQueryParams {
    private String completeTablePath;
    private boolean isJoin;
    private List<FilterOperations> filterOperationsList;
}
