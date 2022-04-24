import {FilterOperation} from "./FilterOperation";
import {JoinOperation} from "./JoinOperation";

export interface CustomQueryParams {
  completeTablePath: string;
  isJoin: boolean;
  tableToJoinPathCatalog: string;
  joinOperator: string;
  joinTableColumnValues: Array<string>;
  filterOperationsList: Array<FilterOperation>;
}
