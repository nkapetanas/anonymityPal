import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { SelectItem } from 'primeng/api';

@Component({
    selector: 'app-select-column',
    templateUrl: './select-column.component.html',
    styleUrls: ['./select-column.component.scss']
})
export class SelectColumnComponent implements OnInit, OnChanges {

    @Input() columnOptions: Array<SelectItem> = [];
    @Input() label: string;
    @Output() getSelectedColumn: EventEmitter<any> = new EventEmitter<any>();

    isDataColumnCollapsed: boolean = false;
    tableValue: string = '';
    isSelectedColumn: boolean = false;
    selectedIndex: number;

    constructor() { }

    ngOnInit(): void {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['label'] && changes['label'].currentValue) {
            this.tableValue = changes['label'].currentValue.split('.')[2]
        }
    }

    collapseColumns() {
        this.isDataColumnCollapsed = !this.isDataColumnCollapsed;
    }

    setSelectedColumn(index: number, column: SelectItem) {
        this.isSelectedColumn = true;
        this.selectedIndex = index;
        this.getSelectedColumn.emit(column.value);
    }

}
