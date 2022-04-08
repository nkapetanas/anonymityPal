import { Component, OnInit, Input, SimpleChanges, OnChanges } from '@angular/core';
import { createDropdownOptions } from '../../../core/utils/dropdown-options.helper'
@Component({
    selector: 'app-quashi-columns-selector',
    templateUrl: './quashi-columns-selector.component.html',
    styleUrls: ['./quashi-columns-selector.component.scss']
})
export class QuashiColumnsSelectorComponent implements OnInit, OnChanges {

    selectedColumns: any[] = [];
    @Input() data: string[] = [];
    options: Array<{value: any, label: string}> = [];

    constructor() { }

    ngOnInit(): void { }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['data'] && changes['data'].currentValue) {
            this.options = createDropdownOptions(changes['data'].currentValue);
        }
    }

    getPrivacyCheck() {

    }

}
