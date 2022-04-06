import { FilterOperation } from "./FilterOperation";
import { JoinOperation } from "./JoinOperation";

export interface CustomQueryParams {
    completeTablePath: string;
    isJoin: boolean;
    joinOperation: JoinOperation;
    filterOperationsList: Array<FilterOperation>;
}
