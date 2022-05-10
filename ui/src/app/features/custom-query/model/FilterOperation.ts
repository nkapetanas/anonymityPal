export interface FilterOperationUI {
    columnName: string,
    columnValues: string;
    filterOperator: string;
    filterLabel: string;
}

export interface FilterOperation {
    columnName: string,
    columnValues: Array<string>;
    filterOperator: string;
}
