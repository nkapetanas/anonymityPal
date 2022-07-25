import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { PrivacyService } from 'src/app/core/services/privacy/privacy-service.service';
import { MessagesService } from '../../services/messages.service';
import { DBRecord } from '../data-table/DataTable.model';

@Component({
    selector: 'app-privacy-check',
    templateUrl: './privacy-check.component.html',
    styleUrls: ['./privacy-check.component.scss'],
    providers: [MessagesService]
})
export class PrivacyCheckComponent implements OnInit {

    @Input() dbRecordList: DBRecord[];
    privacyAttributes: { kAnonymityParam: number | null, lDiversityParam: number | null, dbRecordList: DBRecord[] } = { kAnonymityParam: null, lDiversityParam: null, dbRecordList: [] };

    constructor(
        private privacyService: PrivacyService,
        private messagesService: MessagesService
    ) { }

    ngOnInit(): void {
    }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['dbRecordList'] && changes['dbRecordList'].currentValue) {
            this.privacyAttributes.dbRecordList = changes['dbRecordList'].currentValue;
        }
    }

    getPrivacyCheck() {
        this.privacyService.getQueryResultsPrivacyChecked(this.privacyAttributes).subscribe((response: boolean) => {
            if(response) {
                const summary: string = 'The results are fulfilling the privacy criteria.';
                this.messagesService.showSuccessMessage(summary);
            } else {
                const summary: string = 'The results are not fulfilling the privacy criteria.';
                this.messagesService.showErrorMessage(summary);
            }
            
        })
    }

}
