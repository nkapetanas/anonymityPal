import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { forkJoin } from 'rxjs';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';
import { JoinOperation } from '../model/JoinOperation';

@Component({
    selector: 'app-join-data',
    templateUrl: './join-data.component.html',
    styleUrls: ['./join-data.component.scss']
})
export class JoinDataComponent implements OnInit {

    @Input() selectedTable: string;
    @Input() databaseOptions: Array<SelectItem> = [];
    @Output() onGetJoinQuery: EventEmitter<JoinOperation> = new EventEmitter<JoinOperation>();

    showColumnsModal: boolean = false;
    selectedTableJoin: string = '';
    joinedTable: string = '';
    selectedColumn: string = '';
    selectedColumnJoin: string = '';
    selectedJoinOperation: string = '';
    columnsOptions: Array<SelectItem> = [];
    columnsJoinOptions: Array<SelectItem> = [];
    selectedColumns: string = '';

    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void {
    }

    removeFinalTable() {
        this.joinedTable = '';
        this.removeSelectedColumns();
    }

    matchColumns() {
        this.showColumnsModal = true;
    }

    saveSelectedColumns() {
        this.selectedColumns = this.selectedColumn + ' = ' + this.selectedColumnJoin;
        this.showColumnsModal = false;
    }

    closeColumnsModal() {
        this.selectedColumn = '';
        this.selectedColumnJoin = '';
    }

    removeSelectedColumns() {
        this.selectedColumns = '';
    }

    getSelectedTable(value: string) {
        this.selectedTableJoin = value;
        const getColumnsFromJoinTable = this.queryPrestoService.getColumnsFromTable(this.selectedTableJoin);
        const getColumnsFromTable = this.queryPrestoService.getColumnsFromTable(this.selectedTable);
        forkJoin({getColumnsFromJoinTable, getColumnsFromTable}).subscribe(response=> {
            this.columnsOptions = createDropdownOptions(response.getColumnsFromTable);
            this.columnsJoinOptions = createDropdownOptions(response.getColumnsFromJoinTable);
        })
    }
}
