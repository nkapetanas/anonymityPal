import { Component, Input, OnInit, SimpleChanges, OnChanges } from '@angular/core';
import { DataTable } from './DataTable.model';

@Component({
    selector: 'app-data-table',
    templateUrl: './data-table.component.html',
    styleUrls: ['./data-table.component.scss']
})
export class DataTableComponent implements OnInit, OnChanges {

    @Input() data: DataTable;
    @Input() isPrivacyPreserved?: boolean | null = null;
    orderedColumns: Array<{ label: string, order: number}> = [];

    constructor() { }

    ngOnInit(): void { }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['data'] && changes['data'].currentValue) {
            this.orderedColumns = changes['data'].currentValue.columnNames.map((column: any, index: number) => {
                const columnData = { label: column, order: index };
                return columnData;
            });
            changes['data'].currentValue.dbRecordList.map((dbRecord: any) => {
                if(dbRecord.dbRecordJsonList.length > 0) {
                    dbRecord.dbRecordJsonList.sort((dbRecordJsonPrevious: any, dbRecordJsonNext: any) => {
                        const previousValue: any = this.orderedColumns.find((res: any)=> res.label === dbRecordJsonPrevious.columnName);
                        const nextValue: any = this.orderedColumns.find((res: any)=> res.label === dbRecordJsonNext.columnName);
                        if (previousValue.order < nextValue.order) {
                            return -1;
                        } else if (previousValue > nextValue) {
                            return 1;
                        } else {
                            return 0;
                        }
                    })
                }
            })
        }
    }

}
