import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { DataTable } from 'src/app/shared/tabs-content/data-table/DataTable.model';


@Component({
    selector: 'app-native-query',
    templateUrl: './native-query.component.html',
    styleUrls: ['./native-query.component.scss']
})
export class NativeQueryComponent implements OnInit {

    query: string = '';
    results: DataTable;
    loading: boolean = false;

    constructor(
        private router: Router,
        private queryPrestoService: QueryPrestoService
    ) {
    }

    ngOnInit(): void { }

    returnBack() {
        this.router.navigate(['privacy-buddy/create-question'])
    }

    execute() {
        this.loading = true;
        this.queryPrestoService.getResults(this.query).subscribe((response: DataTable) => {
            this.results = response;
            this.loading = false;
        },
            (error) => {
                // message for error
            })
    }
}
