import {Component, Input, OnInit} from '@angular/core';
import {createDropdownOptions} from "../../../core/utils/dropdown-options.helper";
import {SelectItem} from "primeng/api";
import {QueryPrestoService} from "../../../core/services/queryPresto/query-presto-service.service";
import {FilterOperation} from "../model/FilterOperation";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {
  @Input() selectedTable: string;

  showFilterAdd: boolean = false;
  display: boolean = false;

  displayNextFilter: boolean = false;

  filterLabel: string = 'Add filters to narrow your answer';
  selectedColumn: string;
  selectedFilter: string;
  inputColumnValue: string
  filterOperationsList: Array<FilterOperation> = [];

  secondSelectedColumn: string;

  filterOperationOptionsDropdown: Array<SelectItem> = [];
  availableTableColumnOptionsDropdown: Array<SelectItem> = [];


  columns: Array<any> = [
    'Name', 'Id', 'Age'
  ]

  constructor(private queryPrestoService: QueryPrestoService) {
  }

  ngOnInit(): void {
    this.setFilterOperationOptionsDropdown();
    this.setAvailableTableColumnOptionsDropdown();
  }

  addFilter() {
    let filterOperation: FilterOperation = {
      filterLabel: this.selectedColumn + ' is ' + this.selectedFilter + " " + this.inputColumnValue,
      columnName: this.selectedColumn, columnValues: this.inputColumnValue, filterOperator: this.selectedFilter
    };
    this.filterOperationsList.push(filterOperation);
    this.display = false;

    this.clearSelectedFilters();

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

  onClickChip(filterOperator: FilterOperation) {
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
    this.filterOperationsList.splice(index, 1);
  }
}
