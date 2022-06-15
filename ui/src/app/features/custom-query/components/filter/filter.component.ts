import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import * as _ from 'lodash';
import { SelectItem } from "primeng/api";
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';
import { FilterOperation, FilterOperationUI } from '../../model/FilterOperation';
import { FilterEnum } from './FilterEnum';
import { ModalOperation } from './ModalOperation';

@Component({
    selector: 'app-filter',
    templateUrl: './filter.component.html',
    styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

    @Input() selectedTable: string;
    @Output() onChangeFilterQuery: EventEmitter<Array<FilterOperation>> = new EventEmitter<Array<FilterOperation>>();

    showFilterAdd: boolean = false;
    display: boolean = false;

    displayNextFilter: boolean = false;

    filterLabel: string = 'Add filters to narrow your answer';
    selectedColumn: string;
    selectedFilter: { label: string, sqlOperatorEnum: string };
    inputColumnValue: string;
    inputSecColumnValue: string;
    filterOperationsListChips: Array<FilterOperationUI> = [];

    secondSelectedColumn: string;

    filterOperationOptionsDropdown: Array<SelectItem> = [];
    availableTableColumnOptionsDropdown: Array<SelectItem> = [];
    finalFilterQuery: Array<FilterOperation> = [];

    selectedIndex: number;
    action: number;
    filterEnum = FilterEnum;

    constructor(private queryPrestoService: QueryPrestoService) {
    }

    ngOnInit(): void {
        this.setFilterOperationOptionsDropdown();
        this.setAvailableTableColumnOptionsDropdown();
    }

    handleFilter() {
        switch (this.action) {
            case ModalOperation.ADD:
                this.addFilter();
                break;
            case ModalOperation.UPDATE:
                this.updateFilter();
                break;
        }
    }
    
    addFilter() {
        const columnValues: Array<string> = [this.inputColumnValue];
        if(!_.isNil(this.inputSecColumnValue)) columnValues.push(this.inputSecColumnValue);
        let filterOperationChip: FilterOperationUI = {
            filterLabel: this.selectedColumn + ' is ' + this.selectedFilter.label + " " + this.inputColumnValue + (this.inputSecColumnValue ? ' and ' + this.inputSecColumnValue : ''),
            columnName: this.selectedColumn, columnValues: columnValues, filterOperator: this.selectedFilter.sqlOperatorEnum, filterOperatorValue: this.selectedFilter.label
        };
        this.filterOperationsListChips.push(filterOperationChip);

        const query: FilterOperation = { columnName: this.selectedColumn, columnValues: columnValues, filterOperator: this.selectedFilter.sqlOperatorEnum }
        this.finalFilterQuery.push(query)
        this.onChangeFilterQuery.emit(this.finalFilterQuery);

        this.display = false;
        this.clearSelectedFilters();
    }

    updateFilter() {
        const columnValues: Array<string> = [this.inputColumnValue];
        if(!_.isNil(this.inputSecColumnValue)) columnValues.push(this.inputSecColumnValue);
        let filterOperationChip: FilterOperationUI = {
            filterLabel: this.selectedColumn + ' is ' + this.selectedFilter.label + " " + this.inputColumnValue + (this.inputSecColumnValue ? ' and ' + this.inputSecColumnValue : ''),
            columnName: this.selectedColumn, columnValues: columnValues, filterOperator: this.selectedFilter.sqlOperatorEnum, filterOperatorValue: this.selectedFilter.label
        };
        this.filterOperationsListChips[this.selectedIndex] = filterOperationChip;

        const query: FilterOperation = { columnName: this.selectedColumn, columnValues: columnValues, filterOperator: this.selectedFilter.sqlOperatorEnum }
        this.finalFilterQuery[this.selectedIndex] = query;
        this.onChangeFilterQuery.emit(this.finalFilterQuery);

        this.display = false;
        this.clearSelectedFilters();
    }

    closeModal() {
        this.clearSelectedFilters();
    }

    clearSelectedFilters() {
        this.selectedColumn = "";
        this.selectedFilter = { label: '', sqlOperatorEnum: '' };
        this.inputColumnValue = "";
    }

    showFilterDialog() {
        this.showFilterAdd = true;
        this.action = ModalOperation.ADD;
        this.display = true;
    }

    onClickChip(filterOperator: FilterOperationUI, index: number) {
        this.selectedColumn = filterOperator.columnName;
        this.inputColumnValue = filterOperator.columnValues.length > 0 ? filterOperator?.columnValues[0] : '';
        this.inputSecColumnValue = filterOperator.columnValues.length > 0 ? filterOperator?.columnValues[1] : '';
        this.selectedFilter = { label: filterOperator.filterOperatorValue, sqlOperatorEnum: filterOperator.filterOperator };
        this.selectedIndex = index;
        this.action = ModalOperation.UPDATE;
        this.display = true;
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

    setAvailableTableColumnOptionsDropdown() {
        this.queryPrestoService.getColumnsFromTable(this.selectedTable).subscribe((response: string) => {
            this.availableTableColumnOptionsDropdown = createDropdownOptions(response);
        },
            () => {
                const tables = [
                    'N/A'
                ];
                this.availableTableColumnOptionsDropdown = createDropdownOptions(tables);
            });
    }

    removeFilterOperation(index: number) {
        this.filterOperationsListChips.splice(index, 1);
        this.finalFilterQuery.splice(index, 1);
        this.onChangeFilterQuery.emit(this.finalFilterQuery);
    }
}
