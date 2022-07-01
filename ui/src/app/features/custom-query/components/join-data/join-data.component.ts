import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { forkJoin } from 'rxjs';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';
import { JoinColumns, JoinOperation } from '../../model/JoinOperation';

@Component({
    selector: 'app-join-data',
    templateUrl: './join-data.component.html',
    styleUrls: ['./join-data.component.scss']
})
export class JoinDataComponent implements OnInit {

    @Input() selectedTable: string;
    @Input() databaseOptions: Array<SelectItem> = [];
    @Input() selectedJoinValue: { joinValue: string, icon: string };
    @Output() onChangeJoinQuery: EventEmitter<JoinOperation> = new EventEmitter<JoinOperation>();

    selectedTableJoin: string = '';
    columnsOptions: Array<SelectItem> = [];
    columnsJoinOptions: Array<SelectItem> = [];
    joinOperation: JoinOperation;
    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void { }

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

    getSelectedColumns(value: JoinColumns) {
        this.joinOperation = { tableToJoinPathCatalog: this.selectedTableJoin, columnValues: [ value.selectedColumn, value.selectedJoinColumn ]};
        this.onChangeJoinQuery.emit(this.joinOperation);
    }

    removeJoinPanel() {
        this.joinOperation = { tableToJoinPathCatalog: '', columnValues: []};
        this.selectedJoinValue = { joinValue: '', icon: '' };
        this.selectedTableJoin = '';
        this.onChangeJoinQuery.emit(this.joinOperation);
    }

}