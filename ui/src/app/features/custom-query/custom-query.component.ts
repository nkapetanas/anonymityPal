import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, SelectItem } from 'primeng/api';
import { WizardIndexEnum } from './WizardIndexEnum';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from '../../core/utils/dropdown-options.helper'
import { StepSelectionEnum } from './model/StepSelectionEnum';

@Component({
    selector: 'app-custom-query',
    templateUrl: './custom-query.component.html',
    styleUrls: ['./custom-query.component.scss']
})
export class CustomQueryComponent implements OnInit {

    database: string;
    schema: string;
    table: string;
    selectedTable: string;
    completeCatalog: string;
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
    ) {
    }

    ngOnInit(): void {
        this.items = [
            { label: 'Step 1' },
            { label: 'Step 2' },
            { label: 'Step 3' },
            { label: 'Step 4' },
        ];

        this.route.data.subscribe(data => {
            this.onRouteDataChange(data);
        })
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
                this.completeCatalog = this.completeCatalog + "." + this.table;
                break;
        }
    }

    execute() {

    }

    showJoinData() {
        this.value = 1;
    }

    visualizeData() {
        this.queryPrestoService.getResults(this.completeCatalog).subscribe((response: string) => {
            this.tablesOptions = createDropdownOptions(response);
        },
            (error) => {
                const tables = [
                    'vTable'
                ];
                this.tablesOptions = createDropdownOptions(tables);
            });
    }

    private onRouteDataChange(data: any) {
        this.databasesOptions = createDropdownOptions(data.resolver.databaseData);
    }

    getAvailableDbSchemas(selectedDatabase?: string) {
        const value = selectedDatabase ? selectedDatabase : this.database
        this.queryPrestoService.getAvailableDbSchemas(value).subscribe((response: string) => {
            this.schemasOptions = createDropdownOptions(response);
        },
            (error) => {
                const schemas = [
                    'N/A Schema'
                ];
                this.schemasOptions = createDropdownOptions(schemas);
            });
    }

    private getAvailableSchemaTables() {
        this.completeCatalog = this.database + "." + this.schema;
        this.queryPrestoService.getAvailableSchemaTables(this.completeCatalog).subscribe((response: string) => {
            this.tablesOptions = createDropdownOptions(response);
        },
            (error) => {
                const tables = [
                    'vTable'
                ];
                this.tablesOptions = createDropdownOptions(tables);
            });
    }

    selectDatabase(database: SelectItem) {
        console.log(database);
    }

    getSelectedTable(value: string) {
        this.selectedTable = value;
    }
}
