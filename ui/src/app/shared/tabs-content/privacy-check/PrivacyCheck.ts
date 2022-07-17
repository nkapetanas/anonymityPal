import { DBRecord } from "../data-table/DataTable.model";

export interface PrivacyCheck {
    dbRecordList: DBRecord[]
    kAnonymityParam: number | null;
    lDiversityParam: number | null;
}