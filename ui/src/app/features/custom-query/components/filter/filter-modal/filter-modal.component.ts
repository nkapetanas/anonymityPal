import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import * as _ from 'lodash';
import { SelectItem } from 'primeng/api';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';
import { JoinDataUI } from '../../../model/CustomQueryParams';
import { FilterOperationUI } from '../../../model/FilterOperation';
import { FilterEnum } from '../FilterEnum';
import { ModalOperation } from '../ModalOperation';

@Component({
    selector: 'app-filter-modal',
    templateUrl: './filter-modal.component.html',
    styleUrls: ['./filter-modal.component.scss']
})
export class FilterModalComponent implements OnInit {

    @Input() display: boolean = false;
    @Input() filterData: FilterOperationUI;
    @Input() completeTablePath: string;
    @Input() filterOperationOptionsDropdown: SelectItem[];
    @Input() action: number;
    @Input() tableFilterData: { tableFilter: any, tableFilterOptions: SelectItem[] };
    @Output() onChangeFilterQuery: EventEmitter<FilterOperationUI> = new EventEmitter<FilterOperationUI>();
    @Output() onAddFilterQuery: EventEmitter<FilterOperationUI> = new EventEmitter<FilterOperationUI>();
    @Output() onClodeModal: EventEmitter<void> = new EventEmitter<void>();

    availableTableColumnOptionsDropdown: SelectItem[];
    filterEnum = FilterEnum;
    
    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    ngOnInit(): void {
        // this.setAvailableTableOptionsDropdown();
    }

    closeModal() {
        this.onClodeModal.emit();
    }

    getValueChangeTable(value: string) {
        // this.setAvailableTableColumnOptionsDropdown(value);
    }

    // setAvailableTableOptionsDropdown() {
    //     let selectedTables: Array<string> = [];
    //     selectedTables.push(this.completeTablePath);
    //     this.joinList.map(join => {
    //         selectedTables.push(join.tableToJoinPathCatalog);
    //     });
    //     selectedTables = _.uniqWith(selectedTables);
    //     this.tableFilterOptions = createDropdownOptions(selectedTables);
    // }

    setAvailableTableColumnOptionsDropdown(selectedTable: string) {
        // this.queryPrestoService.getColumnsFromTable(selectedTable).subscribe((response: string) => {
        //     this.availableTableColumnOptionsDropdown = createDropdownOptions(response);
        // },
        //     () => {
        //         const tables = [
        //             'N/A'
        //         ];
        //         this.availableTableColumnOptionsDropdown = createDropdownOptions(tables);
        //     });
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

    setFilterLabel() {
        const secondColumnValue: string = this.filterData.secondColumnValue ? ' and ' + this.filterData.secondColumnValue : '';
        this.filterData.filterLabel = this.filterData.columnName + ' is ' + this.filterData.filterOperator.label + ' ' + this.filterData.firstColumnValue + secondColumnValue;
    }

    addFilter() {
        this.setFilterLabel();
        this.onAddFilterQuery.emit(this.filterData);
        this.display = false;
    }

    updateFilter() {
        this.setFilterLabel();
        this.onChangeFilterQuery.emit(this.filterData);
        this.display = false;
    }

}
