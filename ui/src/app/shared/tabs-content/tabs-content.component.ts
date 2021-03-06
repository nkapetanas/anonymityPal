import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import * as _ from 'lodash';
import { SelectItem } from 'primeng/api';
import { DataTable } from './data-table/DataTable.model';

@Component({
    selector: 'app-tabs-content',
    templateUrl: './tabs-content.component.html',
    styleUrls: ['./tabs-content.component.scss']
})
export class TabsContentComponent implements OnInit, OnChanges {

    @Input() data: DataTable;
    dataOptions: Array<{ columnName: string, values: Array<SelectItem> }> = [];
    isPrivacyPreserved: boolean;
    constructor() { }

    ngOnInit(): void {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['data'] && changes['data'].currentValue) {
            this.handleQuashiOptions(changes['data'].currentValue);
        }
    }

    handleQuashiOptions(data: DataTable) {
        data.quasiColumns.map(quasiColumn => {
            this.dataOptions.push({ columnName: quasiColumn, values: [] });
        });
        data.dbRecordList.map(dbRecord => {
            dbRecord.dbRecordJsonList.map(dbRecordJson => {
                const index: number = _.findIndex(this.dataOptions, function (o) { return o.columnName === dbRecordJson.columnName; });
                if(index >= 0) {
                    this.dataOptions[index].values.push({ label: dbRecordJson.recordValue, value: { quasiColumn: dbRecordJson.columnName, valueToCheck: dbRecordJson.recordValue } });
                    this.dataOptions[index].values = _.uniqWith(this.dataOptions[index].values, _.isEqual);
                }
            })
        });
    }

    getPrivacyPreserved(value: boolean) {
        this.isPrivacyPreserved = value;
    }

}
