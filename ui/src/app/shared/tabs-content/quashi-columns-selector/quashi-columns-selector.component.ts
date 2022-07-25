import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import * as _ from 'lodash';
import { SelectItem } from 'primeng/api';
import { PrivacyService } from 'src/app/core/services/privacy/privacy-service.service';
import { MessagesService } from '../../services/messages.service';
import { DBRecord } from '../data-table/DataTable.model';
import { PrivacyPreservation, QueryResults } from './QueryResults.model';

@Component({
    selector: 'app-quashi-columns-selector',
    templateUrl: './quashi-columns-selector.component.html',
    styleUrls: ['./quashi-columns-selector.component.scss'],
    providers: [MessagesService]
})
export class QuashiColumnsSelectorComponent implements OnInit {

    selectedColumns: Array<{ columnName: string, values: Array<SelectItem> }> = [];
    @Input() dataOptions: Array<{ columnName: string, values: Array<SelectItem> }>;
    @Input() dbRecordListData: Array<DBRecord>;
    @Output() isPrivacyPreserved: EventEmitter<boolean> = new EventEmitter<boolean>();
    selectedValue: Array<{ quasiColumn: string, valueToCheck: string }> = [];

    constructor(
        private privacyService: PrivacyService,
        private messagesService: MessagesService
    ) { }

    ngOnInit(): void { }

    checkPrivacyPreservation() {
        const query: QueryResults = {
            dbRecordList: this.dbRecordListData,
            quasiColumnsToCheck: this.selectedValue
        }
        this.privacyService.checkPrivacyPreservation(query).subscribe((response: PrivacyPreservation) => {
            this.isPrivacyPreserved.emit(response.privacyCheckResult);
            if(!_.isNull(response.resultMessage)) {
                this.messagesService.showErrorMessage(response.resultMessage);
            }
            
        })
    }

    valueChangeColumns(event: any) {
        this.selectedValue = this.selectedValue.filter(res => event.value.some((response: any) => response.columnName === res.quasiColumn));
    }

}
