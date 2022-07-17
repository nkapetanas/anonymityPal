import { string } from "postcss-selector-parser";

export class FilterOperationUI {
    completeTablePathToFilter: string;
    columnName: string;
    firstColumnValue: string;
    secondColumnValue: string;
    filterOperator: FilterOperatorUI;
    filterOperatorValue: string;
    filterLabel: string;

    constructor() {
        this.completeTablePathToFilter = '';
        this.columnName = '';
        this.firstColumnValue = '';
        this.secondColumnValue = '';
        this.filterOperator = {
            label: '',
            sqlOperatorEnum: null
        };
        this.filterLabel = '';
    }
}

export interface FilterOperatorUI {
    label: string;
    sqlOperatorEnum: number | null;
}

export interface FilterOperation {
    completeTablePathToFilter: string;
    columnName: string,
    columnValues: Array<string>;
    filterOperator: string;
}
