import { DBRecord } from "../data-table/DataTable.model";

export interface QueryResults {
    dbRecordList: Array<DBRecord>;
    quasiColumnsToCheck: Array<QuasiKeyPairValue>;
    isPrivacyPreserved?: boolean;
}

interface QuasiKeyPairValue {
    quasiColumn: string;
    valueToCheck: string;
}

export interface PrivacyPreservation {
    privacyCheckResult: boolean;
    resultMessage: string | null;
}