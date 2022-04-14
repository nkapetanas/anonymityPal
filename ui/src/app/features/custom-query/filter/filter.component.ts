import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { createDropdownOptions } from "../../../core/utils/dropdown-options.helper";
import { SelectItem } from "primeng/api";
import { QueryPrestoService } from "../../../core/services/queryPresto/query-presto-service.service";
import { FilterOperation, FilterOperationUI } from "../model/FilterOperation";

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
    selectedFilter: string;
    inputColumnValue: string;
    filterOperationsListChips: Array<FilterOperationUI> = [];

    secondSelectedColumn: string;

    filterOperationOptionsDropdown: Array<SelectItem> = [];
    availableTableColumnOptionsDropdown: Array<SelectItem> = [];
    finalFilterQuery: Array<FilterOperation> = [];

    constructor(private queryPrestoService: QueryPrestoService) {
    }

    ngOnInit(): void {
        this.setFilterOperationOptionsDropdown();
        this.setAvailableTableColumnOptionsDropdown();
    }

    addFilter() {
        let filterOperationChip: FilterOperationUI = {
            filterLabel: this.selectedColumn + ' is ' + this.selectedFilter + " " + this.inputColumnValue,
            columnName: this.selectedColumn, columnValues: this.inputColumnValue, filterOperator: this.selectedFilter
        };
        this.filterOperationsListChips.push(filterOperationChip);
        this.display = false;

        this.clearSelectedFilters();
        const query: FilterOperation = {columnName: this.selectedColumn, columnValues: [ this.inputColumnValue ], filterOperator: this.selectedFilter }
        this.finalFilterQuery.push(query)
        this.onChangeFilterQuery.emit(this.finalFilterQuery);
    }

    clearSelectedFilters() {
        this.selectedColumn = "";
        this.selectedFilter = "";
        this.inputColumnValue = "";
    }

    showFilterDialog() {
        this.showFilterAdd = true;
        this.display = true;
    }

    onClickChip(filterOperator: FilterOperationUI) {
        this.selectedColumn = filterOperator.columnName;
        this.inputColumnValue = filterOperator.columnValues;
        this.selectedFilter = filterOperator.filterOperator;
        this.display = true;
    }

    setFilterOperationOptionsDropdown() {
        this.queryPrestoService.getFilterOperationOptions().subscribe((response: string) => {
            this.filterOperationOptionsDropdown = createDropdownOptions(response);
        },
            (error) => {
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
            (error) => {
                const tables = [
                    'N/A'
                ];
                this.availableTableColumnOptionsDropdown = createDropdownOptions(tables);
            });
    }

    removeFilterOperation(index: number) {
        this.filterOperationsListChips.splice(index, 1);
    }
}
