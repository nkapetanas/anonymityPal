export interface DataTable {
    columnNames: Array<string>;
    dbRecordList: Array<DBRecord>;
    quasiColumns: Array<string>;
}


interface DBRecord {
    dbRecordJsonList: Array<DBRecordJson>;
}

export interface DBRecordJson {
    columnName: string;
    recordValue: string;
}