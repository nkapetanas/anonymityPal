package com.research.privacy.anonymity.pal.api.params;

import com.research.privacy.anonymity.pal.services.customquery.FilterOperations;
import com.research.privacy.anonymity.pal.services.customquery.JoinOperation;
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
    private JoinOperation joinOperation;
    private List<FilterOperations> filterOperationsList;
}
