package com.research.privacy.anonymity.pal.services.customquery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinOperation {
    private String tableToJoinPathCatalog;
    private List<String> columnValues;
}
