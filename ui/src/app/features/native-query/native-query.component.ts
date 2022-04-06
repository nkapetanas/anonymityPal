import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';


@Component({
    selector: 'app-native-query',
    templateUrl: './native-query.component.html',
    styleUrls: ['./native-query.component.scss']
})
export class NativeQueryComponent implements OnInit {

    query: string = '';
    results: any;
    quasiColumns: Array<any>;
    columnNames: Array<any>;
    dbRecordList: Array<any>;
    loading: boolean = false;


    constructor(
        private router: Router,
        private queryPrestoService: QueryPrestoService
    ) {
    }

    ngOnInit(): void {

    }

    returnBack() {
        this.router.navigate(['privacy-buddy/create-question'])
    }

    execute() {
        this.loading = true;
        this.queryPrestoService.getResults(this.query).subscribe(res => {
            this.loading = false;
            this.mapResults(res);
            this.results = res;
        },
            (error) => {
                // message for error
            })
    }

    private mapResults(response: any) {
        if (response) {
            this.quasiColumns = response.quasiColumns;
            this.columnNames = response.columnNames;
            this.results = {
                columnNames: response.columnNames, dbRecordList: response.dbRecordList
            }
        }
    }
}
