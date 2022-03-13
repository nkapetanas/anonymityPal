import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectItem } from 'primeng/api';
import { WizardIndexEnum } from './WizardIndexEnum';
import { MenuItem } from 'primeng/api'
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from '../../core/utils/dropdown-options.helper'
import { CustomQueryResolver } from './services/custom-query.resolver';

@Component({
    selector: 'app-custom-query',
    templateUrl: './custom-query.component.html',
    styleUrls: ['./custom-query.component.scss']
})
export class CustomQueryComponent implements OnInit {

    database: string;
    schema: string;
    table: string;
    databasesOptions: Array<SelectItem> = [];
    schemasOptions: Array<SelectItem> = [];
    tablesOptions: Array<SelectItem> = [];
    stepIndex: number = 0;
    wizardIndexes = WizardIndexEnum;
    items: MenuItem[];
    results: Array<any>;
    options: any[];
    value: number;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void {
        this.items = [
            { label: 'Step 1' },
            { label: 'Step 2' },
            { label: 'Step 3' },
            { label: 'Step 4' },
        ];

        this.options = [
            { icon: 'pi pi-circle', value: 1 }
        ];

        this.route.data.subscribe(data=> {
            this.onRouteDataChange(data);
        })
    }

    onRouteDataChange(data: any) {
        this.databasesOptions = createDropdownOptions(data.resolver.databaseData);
    }

    returnBack() {
        this.router.navigate(['privacy-buddy/create-question'])
    }

    returnToPreviousStep() {
        this.stepIndex -= 1;
    }

    goToNextStep() {
        this.stepIndex += 1;
        // TODO: Block page
        switch (this.stepIndex) {
            case (WizardIndexEnum.STEP_2):
                this.getAvailableDbSchemas();
                break;
            case (WizardIndexEnum.STEP_3):
                this.getAvailableSchemaTables();
                break;
            case (WizardIndexEnum.STEP_4):
                break;
        }
    }

    getAvailableDbSchemas() {
        this.queryPrestoService.getAvailableDbSchemas(this.database).subscribe((response: string) => {
            this.schemasOptions = createDropdownOptions(response);
        },
        (error)=> {
            const schemas = [
                'Schema 1','Schema 2','Schema 3','Schema 4'
            ];
            this.schemasOptions = createDropdownOptions(schemas);
        });
    }

    getAvailableSchemaTables() {
        this.queryPrestoService.getAvailableSchemaTables(this.schema).subscribe((response: string) => {
            this.tablesOptions = createDropdownOptions(response);
        },
        (error)=> {
            const tables = [
                'Table 1','Table 2','Table 3','Table 4'
            ];
            this.tablesOptions = createDropdownOptions(tables);
        });
    }

}
