import { FilterOperation } from "./FilterOperation";

export interface CustomQueryParams {
  completeTablePath: string;
  isJoin: boolean;
  tableToJoinPathCatalog: string;
  joinOperator: string;
  joinTableColumnValues: Array<string>;
  filterOperationsList: Array<FilterOperation>;
}
