import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { PrivacyService } from './core/services/privacy/privacy-service.service';
import { QueryPrestoService } from './core/services/queryPresto/query-presto-service.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {

    title = 'privacy-buddy-ui';
    items: MenuItem[] = [];
    activeItem: MenuItem;
    query: string = '';

    results: any[] = []; // TODO: type of results
    columns: Array<{field: string, header: string}> = [];

    constructor(private privacyService: PrivacyService, 
        private queryPrestoService: QueryPrestoService,
        private router: Router) { }

    ngOnInit() {
        // this.queryPrestoService.getResults().then(data => this.results = data);

    }


    /**
     * Get the table results
     * @returns 
     */
    getResults() {
        return this.queryPrestoService.getResults(this.query).subscribe(response => { // TODO: type of response
            console.log(response);
            // TODO: fill the table with data from response
        });
    }

    askQuestion() {
        debugger;
        this.router.navigate(['privacy-buddy/create-question']);
    }

    redirectToHome() {
        this.router.navigate(['privacy-buddy']);
    }
}
