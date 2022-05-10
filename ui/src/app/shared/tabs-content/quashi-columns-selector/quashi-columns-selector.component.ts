import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { DBRecord } from '../data-table/DataTable.model';
import { QueryResults } from './QueryResults.model';
@Component({
    selector: 'app-quashi-columns-selector',
    templateUrl: './quashi-columns-selector.component.html',
    styleUrls: ['./quashi-columns-selector.component.scss']
})
export class QuashiColumnsSelectorComponent implements OnInit {

    selectedColumns: Array<{ columnName: string, values: Array<SelectItem> }> = [];
    @Input() dataOptions: Array<{ columnName: string, values: Array<SelectItem> }>;
    @Input() dbRecordListData: Array<DBRecord>;
    @Output() isPrivacyPreserved: EventEmitter<boolean> = new EventEmitter<boolean>();
    selectedValue: Array<{ quasiColumn: string, valueToCheck: string }> = [];

    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void { }

    getPrivacyCheck() {
        const query: QueryResults = {
            dbRecordList: this.dbRecordListData,
            quasiColumnsToCheck: this.selectedValue
        }
        this.queryPrestoService.checkPrivacyPreservation(query).subscribe(response => {
            this.isPrivacyPreserved.emit(response);
        })
    }

}
