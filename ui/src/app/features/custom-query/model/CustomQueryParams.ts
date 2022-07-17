import { FilterOperation, FilterOperationUI } from "./FilterOperation";

export interface CustomQueryParams {
    completeTablePath: string;
    joinQueryParams: JoinQueryParams[];
    sortBy: string;
    rowLimit: string;
    filterOperationsList: Array<FilterOperation>;
}

export interface JoinQueryParams {
    tableToJoinPathCatalog: string;
    joinOperator: string;
    joinTableColumnValues: Array<string>;
}

export class CustomQueryParamsUI {
    completeTablePath: string;
    joins: JoinDataUI[];
    sortBy: string;
    rowLimit: string;
    filterOperationsList: Array<FilterOperationUI>;

    constructor(){
        this.completeTablePath = ''; 
        this.joins = [];
        this.sortBy = '';
        this.rowLimit = '';
        this.filterOperationsList = [];
    }
}

export class JoinDataUI {
    tableToJoinPathCatalog: string;
    joinOperator: JoinOperatorUI;
    joinTableColumnValues: Array<string>;

    constructor() {
        this.tableToJoinPathCatalog = '';
        this.joinOperator = {
            joinValue: '',
            icon: '',
            tooltip: ''
        };
        this.joinTableColumnValues = [];
    }
}

export interface JoinOperatorUI {
    joinValue: string;
    icon: string;
    tooltip: string;
}