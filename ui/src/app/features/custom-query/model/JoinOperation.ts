export interface JoinOperation {
    tableToJoinPathCatalog: string;
    columnValues: Array<string>;
}

export interface JoinColumns {
    selectedColumn: string;
    selectedJoinColumn: string;
}
