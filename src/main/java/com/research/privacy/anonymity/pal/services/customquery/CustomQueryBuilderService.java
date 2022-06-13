package com.research.privacy.anonymity.pal.services.customquery;

import com.research.privacy.anonymity.pal.api.params.CustomQueryParams;
import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import com.research.privacy.anonymity.pal.common.enums.JoinOperators;
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
    private static final String JOIN_COLUMNS = "%s = %s ";
    private static final String AND_OPERATION = "AND";
    private static final String ORDER_BY = " ORDER BY %s ";
    private static final String ROW_LIMIT = " LIMIT %s ";

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

            String tableToJoinPathCatalog = customQueryParams.getTableToJoinPathCatalog();
            List<String> joinTableColumnValues = customQueryParams.getJoinTableColumnValues();

            if (Utils.isEmpty(tableToJoinPathCatalog) || Utils.isEmpty(joinTableColumnValues)) {
                throw new AnonymityPalException(AP_E_0005);
            }

            JoinOperators joinOperator = JoinOperators.from(customQueryParams.getJoinOperator());

            if (joinOperator == null) {
                throw new AnonymityPalException(AP_E_0005);
            }

            customQuery.append(String.format(joinOperator.getSqlOperation(), tableToJoinPathCatalog));

            final List<String> columnValues = joinTableColumnValues;
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

        final Integer rowLimit = customQueryParams.getRowLimit();
        final String sortBy = customQueryParams.getSortBy();

        if (Utils.isNotEmpty(sortBy)) {
            customQuery.append(String.format(ORDER_BY, sortBy));
        }

        if (rowLimit != null) {
            customQuery.append(String.format(ROW_LIMIT, rowLimit));
        }

        return customQuery.toString();
    }

    private void addFilters(final List<FilterOperations> filterOperationsList, StringBuilder customQuery) {
        customQuery.append(WHERE_OPERATION);

        filterOperationsList.forEach(f -> {
            customQuery.append(AND_OPERATION);
            customQuery.append(String.format(" %s", f.getColumnName()));

            final FilterOperators filterOperator = FilterOperators.from(f.getFilterOperator());
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
