import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { JoinColumns } from 'src/app/features/custom-query/model/JoinOperation';

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
    @Output() onChangeColumns: EventEmitter<JoinColumns> = new EventEmitter<JoinColumns>();

    isOpen: boolean = false;
    isDisabled: boolean = true;
    selectedColumns: JoinColumns = { selectedColumn: '', selectedJoinColumn: '' };
    selectedColumnsValue: string = '';

    constructor() { }

    ngOnInit(): void { }

    removeSelectedColumns() {
        this.selectedColumnsValue = '';
    }

    saveColumns() {
        this.isOpen = false;
        this.selectedColumnsValue = this.selectedColumns.selectedColumn + ' = ' + this.selectedColumns.selectedJoinColumn;
        this.onChangeColumns.emit(this.selectedColumns);
    }

    openOverLayPanel() {
        this.isOpen = true;
    }

    closeOverlayPanel() {
        this.isOpen = false;
    }

    getSelectedColumn(value: string) {
        this.selectedColumns.selectedColumn = value;
        this.handleDisableButton();
    }

    getSelectedJoinColumn(value: string) {
        this.selectedColumns.selectedJoinColumn = value;
        this.handleDisableButton();
    }

    handleDisableButton() {
        if (this.selectedColumns.selectedColumn !== '' && this.selectedColumns.selectedJoinColumn !== '') {
            this.isDisabled = false;
        }
    }

}
