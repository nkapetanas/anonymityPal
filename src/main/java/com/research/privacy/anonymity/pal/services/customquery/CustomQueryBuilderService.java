package com.research.privacy.anonymity.pal.services.customquery;

import com.research.privacy.anonymity.pal.api.params.CustomQueryParams;
import com.research.privacy.anonymity.pal.api.params.JoinQueryParams;
import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import com.research.privacy.anonymity.pal.common.enums.JoinOperators;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode.AP_E_0005;
import static com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode.AP_E_0007;

@Slf4j
@Service
public class CustomQueryBuilderService {

    private static final String SELECT_ALL_OPERATION = "SELECT * FROM %s %s ";
    private static final String WHERE_OPERATION = "WHERE 1 = 1 ";
    private static final String JOIN_COLUMNS = "%s = %s ";
    private static final String AND_OPERATION = "AND";
    private static final String ORDER_BY = " ORDER BY %s ";
    private static final String ROW_LIMIT = " LIMIT %s ";
    private static final String JOIN_OPERATOR_TABLE = "t";

    public String buildQuery(final CustomQueryParams customQueryParams) throws AnonymityPalException {

        final String completeTablePath = customQueryParams.getCompleteTablePath();
        final List<FilterOperations> filterOperationsList = customQueryParams.getFilterOperationsList();
        final List<JoinQueryParams> joinQueryParamsList = customQueryParams.getJoinQueryParams();

        if (Utils.isEmpty(completeTablePath)) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0004);
        }

        StringBuilder customQuery = new StringBuilder();
        int tablesToJoinCounter = 1;

        final String mainSelectedTable = JOIN_OPERATOR_TABLE + tablesToJoinCounter;
        HashMap<String, String> tableAliasNameMap = new HashMap<>();

        customQuery.append(String.format(SELECT_ALL_OPERATION, completeTablePath, mainSelectedTable));
        tableAliasNameMap.put(completeTablePath, mainSelectedTable);

        addJoinOperations(joinQueryParamsList, customQuery, tablesToJoinCounter, tableAliasNameMap);

        if (Utils.isNotEmpty(filterOperationsList)) {
            addFilters(filterOperationsList, customQuery, tableAliasNameMap);
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

    private void addJoinOperations(List<JoinQueryParams> joinQueryParamsList, StringBuilder customQuery, int tablesToJoinCounter, HashMap<String, String> tableAliasNameMap) throws AnonymityPalException {
        if (Utils.isNotEmpty(joinQueryParamsList)) {

            for (JoinQueryParams joinOperation : joinQueryParamsList) {
                String tableToJoinPathCatalog = joinOperation.getTableToJoinPathCatalog();
                List<String> joinTableColumnValues = joinOperation.getJoinTableColumnValues();

                if (Utils.isEmpty(joinQueryParamsList) || Utils.isEmpty(tableToJoinPathCatalog) || Utils.isEmpty(joinTableColumnValues)) {
                    throw new AnonymityPalException(AP_E_0005);
                }

                JoinOperators joinOperator = JoinOperators.from(joinOperation.getJoinOperator());

                if (joinOperator == null) {
                    throw new AnonymityPalException(AP_E_0005);
                }

                tablesToJoinCounter++;
                final String additionalTableToJoin = JOIN_OPERATOR_TABLE + tablesToJoinCounter;
                tableAliasNameMap.put(tableToJoinPathCatalog, additionalTableToJoin);

                customQuery.append(String.format(joinOperator.getSqlOperation(), tableToJoinPathCatalog, additionalTableToJoin));

                final String firstColumnInJoin = joinTableColumnValues.get(0);
                final String secondColumnInJoin = joinTableColumnValues.get(1);

                if (Utils.isEmpty(firstColumnInJoin) || Utils.isEmpty(secondColumnInJoin)) {
                    throw new AnonymityPalException(AP_E_0007);
                }
                customQuery.append(String.format(JOIN_COLUMNS, firstColumnInJoin, additionalTableToJoin + "." + secondColumnInJoin));
            }
        }
    }

    private void addFilters(final List<FilterOperations> filterOperationsList, StringBuilder customQuery, final HashMap<String, String> tableAliasNameMap) {
        customQuery.append(WHERE_OPERATION);

        filterOperationsList.forEach(f -> {
            customQuery.append(AND_OPERATION);

            final String completeTablePathToFilter = f.getCompleteTablePathToFilter();
            final String tableAliasName = tableAliasNameMap.get(completeTablePathToFilter);

            customQuery.append(String.format(" %s", tableAliasName + "." + f.getColumnName()));

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
