import { Component, Input, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/api';

@Component({
    selector: 'app-create-query',
    templateUrl: './create-query.component.html',
    styleUrls: ['./create-query.component.scss']
})
export class CreateQueryComponent implements OnInit {

    @Input() completeCatalog: string;
    @Input() databasesOptions: Array<SelectItem> = [];

    joinDataOption: Array<SelectItem> = [];
    joinDataValue: number[] = [];

    constructor() { }

    ngOnInit(): void {
        this.joinDataOption = [
            { label: 'Join', value: 1 }
        ];
    }

}
