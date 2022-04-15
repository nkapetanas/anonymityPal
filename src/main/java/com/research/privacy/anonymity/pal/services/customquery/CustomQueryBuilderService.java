package com.research.privacy.anonymity.pal.services.customquery;

import com.research.privacy.anonymity.pal.api.params.CustomQueryParams;
import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode.AP_E_0005;
import static com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode.AP_E_0007;

@Slf4j
@Service
public class CustomQueryBuilderService {

    private static final String SELECT_ALL_OPERATION = "SELECT * FROM %s t1 ";
    private static final String WHERE_OPERATION = "WHERE 1 = 1 ";
    private static final String LEFT_JOIN = "LEFT JOIN %s t2 ON ";
    private static final String JOIN_COLUMNS = "%s = %s ";
    private static final String AND_OPERATION = "AND";

    public String buildQuery(final CustomQueryParams customQueryParams) throws AnonymityPalException {

        final String completeTablePath = customQueryParams.getCompleteTablePath();
        final List<FilterOperations> filterOperationsList = customQueryParams.getFilterOperationsList();
        final boolean isJoinOperations = customQueryParams.isJoin();

        if (Utils.isEmpty(completeTablePath)) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0004);
        }

        StringBuilder customQuery = new StringBuilder();
        customQuery.append(String.format(SELECT_ALL_OPERATION, completeTablePath));

        if (isJoinOperations) {

            if (Utils.isEmpty(customQueryParams.getTableToJoinPathCatalog()) || Utils.isEmpty(customQueryParams.getJoinTableColumnValues())) {
                throw new AnonymityPalException(AP_E_0005);
            }

            customQuery.append(String.format(LEFT_JOIN, customQueryParams.getTableToJoinPathCatalog()));

            final List<String> columnValues = customQueryParams.getJoinTableColumnValues();
            final String firstColumnInJoin = columnValues.get(0);
            final String secondColumnInJoin = columnValues.get(1);

            if (Utils.isEmpty(firstColumnInJoin) || Utils.isEmpty(secondColumnInJoin)) {
                throw new AnonymityPalException(AP_E_0007);
            }
            customQuery.append(String.format(JOIN_COLUMNS, firstColumnInJoin, secondColumnInJoin));
        }

        if (Utils.isNotEmpty(filterOperationsList)) {
            addFilters(filterOperationsList, customQuery);
        }

        return customQuery.toString();
    }

    private void addFilters(final List<FilterOperations> filterOperationsList, StringBuilder customQuery) {
        customQuery.append(WHERE_OPERATION);

        filterOperationsList.forEach(f -> {
            customQuery.append(AND_OPERATION);
            customQuery.append(String.format(" %s", f.getColumnName()));

            final FilterOperators filterOperator = FilterOperators.fromField(f.getFilterOperator());
            customQuery.append(Utils.isNotEmpty(filterOperator) ? filterOperator.getSqlOperation() : null);

            final List<String> columnValues = f.getColumnValues();

            if (!FilterOperators.BETWEEN.equals(filterOperator)) {
                customQuery.append(String.format("'%s'", columnValues.get(0)));
                return;
            }

            customQuery.append(columnValues.get(0));
            customQuery.append(AND_OPERATION);
            customQuery.append(columnValues.get(1));
        });
    }
}
