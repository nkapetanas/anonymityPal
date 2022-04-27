import { Component, OnInit, Input, SimpleChanges, OnChanges } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { createDropdownOptions } from '../../../core/utils/dropdown-options.helper'
@Component({
    selector: 'app-quashi-columns-selector',
    templateUrl: './quashi-columns-selector.component.html',
    styleUrls: ['./quashi-columns-selector.component.scss']
})
export class QuashiColumnsSelectorComponent implements OnInit, OnChanges {

    selectedColumns: any[] = [];
    @Input() data:  Array<{ columnName: string, values: Array<SelectItem> }>;
    selectedValue: Array<string> = [];

    constructor() { }

    ngOnInit(): void { }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['data'] && changes['data'].currentValue) {

        }
    }

    getPrivacyCheck() {

    }

}
