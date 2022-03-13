import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { WizardIndexEnum } from '../custom-query/WizardIndexEnum';


@Component({
    selector: 'app-native-query',
    templateUrl: './native-query.component.html',
    styleUrls: ['./native-query.component.scss']
})
export class NativeQueryComponent implements OnInit {

    query: string = '';
    results: Array<any>;

    constructor(
        private router: Router,
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void {
        
    }

    returnBack() {
        this.router.navigate(['privacy-buddy/create-question'])
    }

    execute() {
        this.queryPrestoService.getResults(this.query).subscribe(res=> {
            console.log(res);
            this.results = res;
        },
        (error) => {
            // message for error
        })
    }

}
