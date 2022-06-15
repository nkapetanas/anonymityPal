import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import * as _ from 'lodash';

@Component({
    selector: 'app-row-limit',
    templateUrl: './row-limit.component.html',
    styleUrls: ['./row-limit.component.scss']
})
export class RowLimitComponent implements OnInit {

    @Output() onChange: EventEmitter<string> = new EventEmitter<string>();
    value: number;
    
    constructor() { }

    ngOnInit(): void {
    }

    changeValue(event: { originalEvent: KeyboardEvent, value: number }): void {
        const rowLimit: string = !_.isNil(event.value) ? event.value.toString() : '';
        this.onChange.emit(rowLimit);
    }

}
