import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SelectItem } from 'primeng/api';
import { PrivacyService } from 'src/app/core/services/privacy/privacy-service.service';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { MessagesService } from 'src/app/shared/services/messages.service';
import { DataTable } from 'src/app/shared/tabs-content/data-table/DataTable.model';
import { createDropdownOptions } from '../../core/utils/dropdown-options.helper'
import { CustomQueryParams, CustomQueryParamsUI, JoinDataUI, JoinQueryParams } from './model/CustomQueryParams';
import { FilterOperation, FilterOperationUI } from './model/FilterOperation';
import { MoreOptionsEnum } from './model/MoreOptionsEnum';
import { FilterTablesService } from './services/filter-tables.service';

@Component({
    selector: 'app-custom-query',
    templateUrl: './custom-query.component.html',
    styleUrls: ['./custom-query.component.scss'],
    providers: [MessagesService]
})
export class CustomQueryComponent implements OnInit {

    customQueryParamsUI: CustomQueryParamsUI = new CustomQueryParamsUI();
    databasesOptions: Array<SelectItem> = [];
    results: DataTable;
    optionValue: Array<number> = [];
    loading: boolean = false;
    columns: any[] = [];
    moreOptionsEnum = MoreOptionsEnum;
    tableFilterOptions: Map<string, SelectItem[]> = new Map<string, SelectItem[]>();
    readonly moreOptions: Array<SelectItem> = [
        { icon: 'pi pi-sort-alt', label: 'Sort', value: MoreOptionsEnum.SORT },
        { icon: 'pi pi-list', label: 'Row limit', value: MoreOptionsEnum.ROW_LIMIT },
    ]

    constructor(
        private route: ActivatedRoute,
        private privacyService: PrivacyService,
        private queryPrestoService: QueryPrestoService,
        private messagesService: MessagesService,
        private filterTableService: FilterTablesService) {
    }

    ngOnInit(): void {
        this.route.data.subscribe(data => {
            this.onRouteDataChange(data);
        })
    }

    execute() {
        debugger;
        const joinDataArray: JoinQueryParams[] = this.customQueryParamsUI.joins.map(joinDataUI=> { 
            const joinData: JoinQueryParams = {
                tableToJoinPathCatalog: joinDataUI.tableToJoinPathCatalog,
                joinOperator: joinDataUI.joinOperator.joinValue,
                joinTableColumnValues: joinDataUI.joinTableColumnValues
            }
            return joinData;
        })

        const filterDataArray: FilterOperation[] = this.customQueryParamsUI.filterOperationsList.map(filterDataUI => {
            const filterData: FilterOperation = {
                completeTablePathToFilter: filterDataUI.completeTablePathToFilter,
                columnName: filterDataUI.columnName,
                columnValues: [filterDataUI.firstColumnValue, filterDataUI.secondColumnValue],
                filterOperator: filterDataUI.filterOperator.label
            }
            return filterData;
        })

        const query: CustomQueryParams = {
            completeTablePath: this.customQueryParamsUI.completeTablePath,
            joinQueryParams: joinDataArray,
            sortBy: this.customQueryParamsUI.sortBy,
            rowLimit: this.customQueryParamsUI.rowLimit,
            filterOperationsList: filterDataArray
        }

        this.loading = true;
        this.privacyService.getCustomQueryResults(query).subscribe(response => {
            this.results = response;
            this.loading = false;
        },
        (error) => {
            const summary: string = 'The query does not fulfill the criteria.';
            this.messagesService.showErrorMessage(summary);
            this.loading = false;
        });
    }

    reset() {
        this.customQueryParamsUI = new CustomQueryParamsUI();
        this.optionValue = [];
        this.results = {
            columnNames: [],
            dbRecordList: [],
            quasiColumns: []
        };
    }

    /**
     * @description Get specific Join data from array
     * @param data JoinDataUI
     * @param index number
     */
    getJoinQueryData(data: {joinData: JoinDataUI, columnsJoinOptions: SelectItem[]}, index: number) {
        this.customQueryParamsUI.joins[index] = data.joinData;
        this.tableFilterOptions.set(data.joinData.tableToJoinPathCatalog, data.columnsJoinOptions);
        this.filterTableService.setFilterTables(this.tableFilterOptions);
        console.log(this.tableFilterOptions)
    }

    /**
     * @description Set selected table
     */
    getSelectedTable(value: string) {
        this.customQueryParamsUI.completeTablePath = value;
        this.setAvailableTableColumn();
    }

    /**
     * @description Set columns of selected table
     */
    setAvailableTableColumn() {
        this.queryPrestoService.getColumnsFromTable(this.customQueryParamsUI.completeTablePath).subscribe((response: any) => {
            this.columns =  response;
            this.tableFilterOptions.set(this.customQueryParamsUI.completeTablePath, this.columns);
            this.filterTableService.setFilterTables(this.tableFilterOptions);
        },
            () => {
                this.columns =  [];
            });
    }

    /**
     * @description Get the value of Filter Array
     * @param value Array<FilterOperation>
     */
    getFilterQuery(value: Array<FilterOperationUI>) {
        this.customQueryParamsUI.filterOperationsList = value;
    }

    /**
     * @description Get the value of Row Limit
     * @param value string
     */
    getRowLimit(value: string) {
        this.customQueryParamsUI.rowLimit = value;
    }

    /**
     * @description Get the value of Sort By
     * @param value string
     */
    getSortBy(value: string) {
        this.customQueryParamsUI.sortBy = value;
    }

    /**
     * @description Add new join object in array
     */
    addJoin() {
        this.customQueryParamsUI.joins.push(new JoinDataUI());
    }

    /**
     * @description Remove from Join Array the specific index
     * @param index number
     */
    removeJoinPanel(index: number, tableToJoinPathCatalog: string) {
        this.tableFilterOptions.delete(tableToJoinPathCatalog);
        this.filterTableService.setFilterTables(this.tableFilterOptions);
        this.customQueryParamsUI.joins.splice(index, 1);
    }

    private onRouteDataChange(data: any) {
        this.databasesOptions = createDropdownOptions(data.resolver.databaseData);
    }
}
