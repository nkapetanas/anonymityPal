export interface FilterOperationUI {
    columnName: string,
    columnValues: string;
    filterOperator: string; // EQUAL_TO
    filterOperatorValue: string; // Equal to
    filterLabel: string;
}

export interface FilterOperation {
    columnName: string,
    columnValues: Array<string>;
    filterOperator: string;
}
