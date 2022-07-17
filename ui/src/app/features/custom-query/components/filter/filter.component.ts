import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import * as _ from 'lodash';
import { SelectItem } from "primeng/api";
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';
import { CustomQueryParamsUI } from '../../model/CustomQueryParams';
import { FilterOperationUI } from '../../model/FilterOperation';
import { FilterTablesService } from '../../services/filter-tables.service';
import { ModalOperation } from './ModalOperation';

@Component({
    selector: 'app-filter',
    templateUrl: './filter.component.html',
    styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

    @Input() customQueryParams: CustomQueryParamsUI;
    @Output() onChangeFilterQuery: EventEmitter<Array<FilterOperationUI>> = new EventEmitter<Array<FilterOperationUI>>();

    display: boolean = false;
    filterOperationOptionsDropdown: Array<SelectItem> = [];
    selectedFilterOperation: FilterOperationUI;
    selectedIndex: number;
    action: number;
    tableFilterData: { tableFilter: any, tableFilterOptions: SelectItem[]}

    constructor(
        private queryPrestoService: QueryPrestoService,
        private filterTableService: FilterTablesService) {
    }

    ngOnInit(): void {
        this.setFilterOperationOptionsDropdown();

        this.filterTableService.getFilterTables$.subscribe(response => {
            const tableFilterOptions = createDropdownOptions(Array.from( response.keys() ));
            this.tableFilterData = { tableFilter: response, tableFilterOptions: tableFilterOptions}
        })
    }

    removeFilterOperation(index: number) {
        this.customQueryParams.filterOperationsList.splice(index, 1);
    }

    showFilterDialog() {
        this.action = ModalOperation.ADD;
        this.selectedFilterOperation = new FilterOperationUI;
        this.display = true;
    }

    updateChip(filterOperator: FilterOperationUI, index: number) {
        // this.selectedColumn = filterOperator.columnName;
        // this.inputColumnValue = filterOperator.columnValues.length > 0 ? filterOperator?.columnValues[0] : '';
        // this.inputSecColumnValue = filterOperator.columnValues.length > 0 ? filterOperator?.columnValues[1] : '';
        // this.selectedFilter = { label: filterOperator.filterOperatorValue, sqlOperatorEnum: filterOperator.filterOperator };
        this.selectedIndex = index;
        this.action = ModalOperation.UPDATE;
        this.selectedFilterOperation = filterOperator;
        this.display = true;
    }

    addFilter(filterOperationUI: FilterOperationUI) {
        this.customQueryParams.filterOperationsList.push(filterOperationUI);
        this.onChangeFilterQuery.emit(this.customQueryParams.filterOperationsList);
    }

    updateFilter(filterOperationUI: FilterOperationUI) {
        this.customQueryParams.filterOperationsList[this.selectedIndex] = filterOperationUI;
        this.onChangeFilterQuery.emit(this.customQueryParams.filterOperationsList);
    }

    closeModal() {
        this.display = false;
    }

    setFilterOperationOptionsDropdown() {
        this.queryPrestoService.getFilterOperationOptions().subscribe((response: string) => {
            this.filterOperationOptionsDropdown = createDropdownOptions(response, 'label');
        },
            () => {
                const tables = [
                    'N/A'
                ];
                this.filterOperationOptionsDropdown = createDropdownOptions(tables);
            });
    }

}
