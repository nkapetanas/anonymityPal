import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-row-limit',
    templateUrl: './row-limit.component.html',
    styleUrls: ['./row-limit.component.scss']
})
export class RowLimitComponent implements OnInit {

    @Output() onChange: EventEmitter<number | null> = new EventEmitter<number | null>();
    value: number;
    
    constructor() { }

    ngOnInit(): void {
    }

    changeValue(event: { originalEvent: KeyboardEvent, value: number | null }): void {
        this.onChange.emit(event.value);
    }

}
