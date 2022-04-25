import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SelectItem } from 'primeng/api';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { DataTable } from 'src/app/shared/tabs-content/data-table/DataTable.model';
import { createDropdownOptions } from '../../core/utils/dropdown-options.helper'
import { CustomQueryParams } from './model/CustomQueryParams';
import { FilterOperation } from './model/FilterOperation';
import { JoinColumns } from './model/JoinOperation';
import { JoinOperatorsEnum } from './model/JoinOperatorsEnum';

@Component({
    selector: 'app-custom-query',
    templateUrl: './custom-query.component.html',
    styleUrls: ['./custom-query.component.scss']
})
export class CustomQueryComponent implements OnInit {

    selectedTable: string;
    databasesOptions: Array<SelectItem> = [];
    results: DataTable;
    joinDataOption: Array<SelectItem> = [];
    joinDataValue: { joinValue: string, icon: string };
    filterLists: Array<FilterOperation>;
    joinData: JoinColumns;
    loading: boolean = false;

    constructor(
        private route: ActivatedRoute,
        private queryPrestoService: QueryPrestoService) {
    }

    ngOnInit(): void {

        this.joinDataOption = [
            { icon: 'join-icons join-left-icon', label: 'Left outer join', value: { joinValue: JoinOperatorsEnum.LEFT_OUTER_JOIN, icon: 'join-icons join-left-icon' } },
            { icon: 'join-icons join-right-icon', label: 'Right outer join', value: { joinValue: JoinOperatorsEnum.RIGHT_OUTER_JOIN, icon: 'join-icons join-right-icon' } },
            { icon: 'join-icons join-inner-icon', label: 'Inner join', value: { joinValue: JoinOperatorsEnum.INNER_JOIN, icon: 'join-icons join-inner-icon' } },
        ];

        this.route.data.subscribe(data => {
            this.onRouteDataChange(data);
        })
    }

    execute() {
        const query: CustomQueryParams = {
            completeTablePath: this.selectedTable,
            isJoin: this.joinDataValue.joinValue ? true : false,
            tableToJoinPathCatalog: '',
            joinOperator: this.joinDataValue.joinValue,
            joinTableColumnValues: this.joinData ? [this.joinData.selectedColumn, this.joinData.selectedJoinColumn] : [],
            filterOperationsList: this.filterLists
        }

        this.loading = true;
        this.queryPrestoService.getCustomQueryResults(query).subscribe(response => {
            this.results = response;
            this.loading = false;
        });
    }

    private onRouteDataChange(data: any) {
        this.databasesOptions = createDropdownOptions(data.resolver.databaseData);
    }

    getSelectedTable(value: string) {
        this.selectedTable = value;
    }

    getFilterQuery(value: Array<FilterOperation>) {
        this.filterLists = value;
    }

    getJoinQuery(value: JoinColumns) {
        this.joinData = value;
    }
}
