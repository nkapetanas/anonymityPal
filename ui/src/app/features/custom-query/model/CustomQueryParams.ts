import { FilterOperation } from "./FilterOperation";

export interface CustomQueryParams {
    completeTablePath: string;
    isJoin: boolean;
    tableToJoinPathCatalog: string;
    joinOperator: string;
    sortBy: string;
    rowLimit: string;
    joinTableColumnValues: Array<string>;
    filterOperationsList: Array<FilterOperation>;
}
