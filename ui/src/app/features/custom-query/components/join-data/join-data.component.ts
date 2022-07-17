import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import * as _ from 'lodash';
import { SelectItem } from 'primeng/api';
import { forkJoin } from 'rxjs';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';
import { JoinDataUI } from '../../model/CustomQueryParams';
import { JoinColumns } from '../../model/JoinOperation';
import { JoinOperatorsEnum } from '../../model/JoinOperatorsEnum';

@Component({
    selector: 'app-join-data',
    templateUrl: './join-data.component.html',
    styleUrls: ['./join-data.component.scss']
})
export class JoinDataComponent implements OnInit {

    @Input() selectedTable: string;
    @Input() joinData: JoinDataUI;
    @Input() databaseOptions: Array<SelectItem> = [];
    @Output() onChangeJoinQuery: EventEmitter<{joinData: JoinDataUI, columnsJoinOptions: SelectItem[]}> = new EventEmitter<{joinData: JoinDataUI, columnsJoinOptions: SelectItem[]}>();
    // @Output() onGetFilterTables: EventEmitter<string[]> = new EventEmitter<string[]>();

    columnsOptions: Array<SelectItem> = [];
    columnsJoinOptions: Array<SelectItem> = [];
    readonly joinDataOption: Array<SelectItem> = [
        { icon: 'join-icons join-left-icon', label: 'Left outer join', value: { joinValue: JoinOperatorsEnum.LEFT_OUTER_JOIN, icon: 'join-icons join-left-icon', tooltip: 'Left join' } },
        { icon: 'join-icons join-right-icon', label: 'Right outer join', value: { joinValue: JoinOperatorsEnum.RIGHT_OUTER_JOIN, icon: 'join-icons join-right-icon', tooltip: 'Right join' } },
        { icon: 'join-icons join-inner-icon', label: 'Inner join', value: { joinValue: JoinOperatorsEnum.INNER_JOIN, icon: 'join-icons join-inner-icon', tooltip: 'Inner join' } },
    ];

    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void {
        this.joinData.joinOperator = (_.head(this.joinDataOption))?.value;
    }

    getSelectedTable(value: string) {
        this.joinData.tableToJoinPathCatalog = value;
        if (this.joinData.tableToJoinPathCatalog !== '') {
            const getColumnsFromJoinTable = this.queryPrestoService.getColumnsFromTable(this.joinData.tableToJoinPathCatalog);
            const getColumnsFromTable = this.queryPrestoService.getColumnsFromTable(this.selectedTable);
            forkJoin({ getColumnsFromJoinTable, getColumnsFromTable }).subscribe(response => {
                this.columnsOptions = createDropdownOptions(response.getColumnsFromTable);
                this.columnsJoinOptions = createDropdownOptions(response.getColumnsFromJoinTable);
            })
        }
    }

    getSelectedColumns(value: JoinColumns) {
        this.joinData.joinTableColumnValues = [ value.selectedColumn, value.selectedJoinColumn ];
        this.onChangeJoinQuery.emit({ joinData: this.joinData, columnsJoinOptions: this.columnsJoinOptions });
        // let tableForFiltering: any;
        // tableForFiltering[this.joinData.tableToJoinPathCatalog] = this.columnsJoinOptions;
        // this.onGetFilterTables.emit(tableForFiltering);
    }
}
