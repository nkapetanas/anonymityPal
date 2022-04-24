import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';
import { StepSelectionEnum } from 'src/app/features/custom-query/model/StepSelectionEnum';

@Component({
    selector: 'app-overlay-panel-table',
    templateUrl: './overlay-panel-table.component.html',
    styleUrls: ['./overlay-panel-table.component.scss']
})
export class OverlayPanelTableComponent implements OnInit {

    stepSelectionIndex: number;
    stepSelections = StepSelectionEnum;

    isOpen: boolean = false;
    @Input() databasesOptions: Array<SelectItem> = [];
    @Output() onSelectTable: EventEmitter<string> = new EventEmitter<string>();
    schemasOptions: Array<SelectItem> = [];
    tablesOptions: Array<SelectItem> = [];

    database: string = '';
    schema: string = '';
    table: string = '';
    
    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void {
    }

    /**
     * Open overlay panel and reset the panel on database selection
     */
    openOverLayPanel() {
        this.stepSelectionIndex = StepSelectionEnum.DATABASE;
        this.isOpen = true;
    }

    /**
     * Close overlay panel
     */
    closeOverlayPanel() {
        this.isOpen = false;
    }

    getAvailableDbSchemas(selectedDatabase: string) {
        this.database = selectedDatabase;
        this.queryPrestoService.getAvailableDbSchemas(selectedDatabase).subscribe((response: string) => {
            this.schemasOptions = createDropdownOptions(response);
            this.stepSelectionIndex = StepSelectionEnum.SCHEMA;
        },
            (error) => {
                const schemas = [
                    'N/A Schema'
                ];
            });
    }

    getAvailableSchemaTables(selectedSchema: string) {
        const schemaPath: string = this.database + "." + selectedSchema;
        this.schema = selectedSchema;
        this.queryPrestoService.getAvailableSchemaTables(schemaPath).subscribe((response: string) => {
            this.tablesOptions = createDropdownOptions(response);
            this.stepSelectionIndex = StepSelectionEnum.TABLE;
        },
            (error) => {
                const tables = [
                    'vTable'
                ];
            });
    }

    selectTable(selectedTable: string) {
        this.table = this.database + '.' + this.schema + '.' + selectedTable;
        this.onSelectTable.emit(this.table);
        this.isOpen = false;
    }

    returnToDatabaseOptions() {
        this.database = '';
        this.stepSelectionIndex = StepSelectionEnum.DATABASE;
    }

    returnToSchemaOptions() {
        this.schema = '';
        this.stepSelectionIndex = StepSelectionEnum.SCHEMA;
    }

    removeSelectedTable() {
        this.table = '';
        this.onSelectTable.emit(this.table);
    }

}
