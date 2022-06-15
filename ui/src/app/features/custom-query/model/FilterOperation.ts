export interface FilterOperationUI {
    columnName: string,
    columnValues: Array<string>;
    filterOperator: string;
    filterOperatorValue: string;
    filterLabel: string;
}

export interface FilterOperation {
    columnName: string,
    columnValues: Array<string>;
    filterOperator: string;
}
