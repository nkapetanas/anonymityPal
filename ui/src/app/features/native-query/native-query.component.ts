import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PrivacyService } from 'src/app/core/services/privacy/privacy-service.service';
import { MessagesService } from 'src/app/shared/services/messages.service';
import { DataTable } from 'src/app/shared/tabs-content/data-table/DataTable.model';

@Component({
    selector: 'app-native-query',
    templateUrl: './native-query.component.html',
    styleUrls: ['./native-query.component.scss'],
    providers: [MessagesService]
})
export class NativeQueryComponent implements OnInit {

    query: string = '';
    results: DataTable;
    loading: boolean = false;

    constructor(
        private router: Router,
        private privacyService: PrivacyService,
        private messagesService: MessagesService
    ) {
    }

    ngOnInit(): void { }

    returnBack() {
        this.router.navigate(['privacy-buddy/create-question'])
    }

    execute() {
        this.loading = true;
        this.privacyService.getResults(this.query).subscribe((response: DataTable) => {
            this.results = response;
            this.loading = false;
        },
        (error) => {
            const summary: string = 'The query does not fulfill the criteria.';
            this.messagesService.showErrorMessage(summary);
            this.loading = false;
        });
    }
}
