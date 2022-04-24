import { Component, Input, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/api';

@Component({
    selector: 'app-select-matching-columns',
    templateUrl: './select-matching-columns.component.html',
    styleUrls: ['./select-matching-columns.component.scss']
})
export class SelectMatchingColumnsComponent implements OnInit {

    @Input() selectedTableJoin: string;
    @Input() selectedTable: string;
    @Input() joinColumnOptions: Array<SelectItem> = [];
    @Input() columnOptions: Array<SelectItem> = [];

    isOpen: boolean = false;
    selectedColumns: string = '';

    constructor() { }

    ngOnInit(): void {
    }

    removeSelectedColumns() {
        this.selectedColumns = '';
    }

}
