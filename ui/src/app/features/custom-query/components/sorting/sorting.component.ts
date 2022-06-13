import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { createDropdownOptions } from 'src/app/core/utils/dropdown-options.helper';

@Component({
    selector: 'app-sorting',
    templateUrl: './sorting.component.html',
    styleUrls: ['./sorting.component.scss']
})
export class SortingComponent implements OnInit {

    @Input() columns: any[] = [];
    selectedColumn: string;
    columnOptionsDropdown: SelectItem[] = [];
    isOpen: boolean = false;

    constructor() { }

    ngOnInit(): void {
    }
    
    ngOnChanges(changes: SimpleChanges) {
        if(changes['columns'] && changes['columns'].currentValue && changes['columns'].firstChange) {
            this.columnOptionsDropdown = createDropdownOptions(changes['columns'].currentValue);
        }
    }

    openOverLayPanel() {
        this.isOpen = true;
    }

    closeOverlayPanel() {
        this.isOpen = false;
    }

    getSelectedColumn(value: string) {
        this.selectedColumn = value;
        this.closeOverlayPanel();
    }

    removeSelectedColumn() {
        this.selectedColumn = '';
    }
}
