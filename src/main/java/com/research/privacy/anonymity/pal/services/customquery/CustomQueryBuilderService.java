package com.research.privacy.anonymity.pal.services.customquery;

import com.research.privacy.anonymity.pal.api.params.CustomQueryParams;
import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomQueryBuilderService {

    private static final String SELECT_ALL_OPERATION = "SELECT * FROM %s t1 ";
    private static final String WHERE_OPERATION = "WHERE 1 = 1 ";
    private static final String LEFT_JOIN = " LEFT JOIN %s t2 ON ";
    private static final String AND_OPERATION = "AND";

    public String buildQuery(final CustomQueryParams customQueryParams) {

        final String completeTablePath = customQueryParams.getCompleteTablePath();
        final List<FilterOperations> filterOperationsList = customQueryParams.getFilterOperationsList();
        final boolean isJoinOperations = customQueryParams.isJoin();
        final String completeTableToJoinPath = customQueryParams.getCompleteTableToJoinPath();

        if(Utils.isEmpty(completeTablePath)){
            return null;
        }

        StringBuilder customQuery = new StringBuilder();
        customQuery.append(String.format(SELECT_ALL_OPERATION, completeTablePath));

        if(isJoinOperations && Utils.isNotEmpty(completeTableToJoinPath)){
            customQuery.append(String.format(LEFT_JOIN, completeTableToJoinPath));
            // TODO continue
        }

        if(Utils.isNotEmpty(filterOperationsList)){
            addFilters(filterOperationsList, customQuery);
        }

        return customQuery.toString();
    }

    private void addFilters(final List<FilterOperations> filterOperationsList, StringBuilder customQuery) {
        customQuery.append(WHERE_OPERATION);

        filterOperationsList.forEach(f-> {
            customQuery.append(AND_OPERATION);
            customQuery.append(f.getColumnName());

            final FilterOperators filterOperator = f.getFilterOperator();
            customQuery.append(filterOperator);

            final List<String> columnValues = f.getColumnValues();

            if(!FilterOperators.BETWEEN.equals(filterOperator)){
                customQuery.append(columnValues.get(0));
                return;
            }

            customQuery.append(columnValues.get(0));
            customQuery.append(AND_OPERATION);
            customQuery.append(columnValues.get(1));
        });
    }
}
