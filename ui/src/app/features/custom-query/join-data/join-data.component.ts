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
    @Input() selectedJoinValue: { id: number, icon: string };
    @Output() onGetJoinQuery: EventEmitter<JoinOperation> = new EventEmitter<JoinOperation>();

    selectedTableJoin: string = '';
    columnsOptions: Array<SelectItem> = [];
    columnsJoinOptions: Array<SelectItem> = [];
    selectedColumns: string = '';

    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void { }

    removeSelectedColumns() {
        this.selectedColumns = '';
    }

    getSelectedTable(value: string) {
        this.selectedTableJoin = value;
        if (this.selectedTableJoin !== '') {
            const getColumnsFromJoinTable = this.queryPrestoService.getColumnsFromTable(this.selectedTableJoin);
            const getColumnsFromTable = this.queryPrestoService.getColumnsFromTable(this.selectedTable);
            forkJoin({ getColumnsFromJoinTable, getColumnsFromTable }).subscribe(response => {
                this.columnsOptions = createDropdownOptions(response.getColumnsFromTable);
                this.columnsJoinOptions = createDropdownOptions(response.getColumnsFromJoinTable);
            })
        }

    }
}
