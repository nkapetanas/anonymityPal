import { Component, OnInit, Input } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { forkJoin } from 'rxjs';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';

@Component({
    selector: 'app-join-data',
    templateUrl: './join-data.component.html',
    styleUrls: ['./join-data.component.scss']
})
export class JoinDataComponent implements OnInit {

    @Input() selectedTable: any;
    @Input() databaseOptions: Array<SelectItem> = [];

    showTableSelectorModal: boolean = false;
    showColumnsModal: boolean = false;
    selectedDatabaseJoin: string = '';
    selectedSchemaJoin: string = '';
    selectedTableJoin: string = '';
    finalTable: string = '';
    selectedColumn: string = '';
    selectedColumnJoin: string = '';
    schemaOptions: Array<SelectItem> = [];
    tableOptions: Array<SelectItem> = [];
    columnsOptions: Array<SelectItem> = [];
    columnsJoinOptions: Array<SelectItem> = [];

    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void {
    }

    pickTable() {
        this.showTableSelectorModal = true;
    }

    databaseValueChange(event: { originalEvent: PointerEvent, value: string }) {
        console.log(event);
        this.queryPrestoService.getAvailableDbSchemas(event.value).subscribe((response: string) => {
            this.schemaOptions = createDropdownOptions(response);
        },
            (error) => {
                const schemas = [
                    'N/A Schema'
                ];
                this.schemaOptions = createDropdownOptions(schemas);
            });
    }

    schemaValueChange(event: { originalEvent: PointerEvent, value: string }) {
        const completeCatalog = this.selectedDatabaseJoin + "." + event.value;
        this.queryPrestoService.getAvailableSchemaTables(completeCatalog).subscribe((response: string) => {
            this.tableOptions = createDropdownOptions(response);
        },
            (error) => {
                const tables = [
                    'vTable'
                ];
                this.tableOptions = createDropdownOptions(tables);
            });
    }

    saveSelectedJoinTable() {
        this.showTableSelectorModal = false;
        this.finalTable = this.selectedDatabaseJoin + "." + this.selectedSchemaJoin + "." + this.selectedTableJoin;
        const getColumnsFromSelectedTable = this.queryPrestoService.getColumnsFromTable(this.selectedTable);
        const getColumnsFromJoinTable = this.queryPrestoService.getColumnsFromTable(this.finalTable);
        forkJoin({getColumnsFromSelectedTable, getColumnsFromJoinTable}).subscribe(results => {
            console.log(results);
            this.columnsOptions = createDropdownOptions(results['getColumnsFromJoinTable']);
            this.columnsJoinOptions = createDropdownOptions(results['getColumnsFromSelectedTable']);
            
        })
    }

    removeFinalTable() {
        this.finalTable = '';
    }

    closeModal() {
        this.selectedDatabaseJoin = '';
        this.selectedSchemaJoin = '';
        this.selectedTableJoin = '';
        this.schemaOptions = [];
        this.tableOptions = [];
    }

    matchColumns() {
        this.showColumnsModal = true;
    }

    saveSelectedColumns() {
        this.showColumnsModal = false;
    }

    closeColumnsModal() {
        // this.selectedColumn = '';
        // this.selectedColumnJoin = '';
    }

}
