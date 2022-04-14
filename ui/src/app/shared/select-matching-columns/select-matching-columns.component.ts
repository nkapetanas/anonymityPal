import { Component, Input, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/api';

@Component({
    selector: 'app-select-matching-columns',
    templateUrl: './select-matching-columns.component.html',
    styleUrls: ['./select-matching-columns.component.scss']
})
export class SelectMatchingColumnsComponent implements OnInit {

    @Input() joinColumnOptions: Array<SelectItem> = [];
    @Input() columnOptions: Array<SelectItem> = [];

    isOpen: boolean = false;
    selectedColumns: string = '';
    isDataColumnCollapsed: boolean = false;
    isDataJoinColumnCollapsed: boolean = false;

    constructor() { }

    ngOnInit(): void {
    }

    removeSelectedColumns() {
        this.selectedColumns = '';
    }

    collapseColumns() {
        this.isDataColumnCollapsed = !this.isDataColumnCollapsed;
    }

    collapseJoinColumns() {
        this.isDataJoinColumnCollapsed = !this.isDataJoinColumnCollapsed;
    }

}
