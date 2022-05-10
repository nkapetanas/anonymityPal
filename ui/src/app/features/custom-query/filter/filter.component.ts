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
    selectedFilter: { label: string, sqlOperatorEnum: string };
    inputColumnValue: string;
    filterOperationsListChips: Array<FilterOperationUI> = [];

    secondSelectedColumn: string;

    filterOperationOptionsDropdown: Array<SelectItem> = [];
    availableTableColumnOptionsDropdown: Array<SelectItem> = [];
    finalFilterQuery: Array<FilterOperation> = [];

    selectedIndex: number;

    constructor(private queryPrestoService: QueryPrestoService) {
    }

    ngOnInit(): void {
        this.setFilterOperationOptionsDropdown();
        this.setAvailableTableColumnOptionsDropdown();
    }

    addFilter() {
        let filterOperationChip: FilterOperationUI = {
            filterLabel: this.selectedColumn + ' is ' + this.selectedFilter.label + " " + this.inputColumnValue,
            columnName: this.selectedColumn, columnValues: this.inputColumnValue, filterOperator: this.selectedFilter.label
        };
        this.filterOperationsListChips.push(filterOperationChip);

        const query: FilterOperation = { columnName: this.selectedColumn, columnValues: [this.inputColumnValue], filterOperator: 'EQUAL_TO' }
        this.finalFilterQuery.push(query)
        this.onChangeFilterQuery.emit(this.finalFilterQuery);

        this.display = false;
        this.clearSelectedFilters();
    }

    clearSelectedFilters() {
        this.selectedColumn = "";
        this.selectedFilter = { label: '', sqlOperatorEnum: '' };
        this.inputColumnValue = "";
    }

    showFilterDialog() {
        this.showFilterAdd = true;
        this.display = true;
    }

    onClickChip(filterOperator: FilterOperationUI, index: number) {
        this.selectedColumn = filterOperator.columnName;
        this.inputColumnValue = filterOperator.columnValues;
        this.selectedFilter = { label: filterOperator.filterLabel, sqlOperatorEnum: filterOperator.filterOperator };
        this.selectedIndex = index;
        this.display = true;
    }

    setFilterOperationOptionsDropdown() {
        this.queryPrestoService.getFilterOperationOptions().subscribe((response: string) => {
            this.filterOperationOptionsDropdown = createDropdownOptions(response, 'label');
            console.log(this.filterOperationOptionsDropdown)
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
