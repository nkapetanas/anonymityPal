import { Component, Input, OnInit, SimpleChanges, OnChanges } from '@angular/core';

@Component({
    selector: 'app-data-table',
    templateUrl: './data-table.component.html',
    styleUrls: ['./data-table.component.scss']
})
export class DataTableComponent implements OnInit, OnChanges {

    results: any[] = []; // TODO: type of results
    columns: Array<{field: string, header: string}> = [];
    // @Input() dataTable;

    constructor() { }

    ngOnInit(): void {

        this.columns = [
            { field: 'code', header: 'Code' },
            { field: 'name', header: 'Name' },
            { field: 'category', header: 'Category' },
            { field: 'quantity', header: 'Quantity' }
        ];

        this.results = [
            {code: 'code', name: 'name', category: 'category', quantity: 'quantity'}
        ];
    }

    ngOnChanges(_changes: SimpleChanges) {
        // if(changes.dataTable && changes.dataTable.currentValue && changes.dataTable.firstChange) {
        //     this.columns = changes.dataTable.currentValue.columns;
        //     this.results = changes.dataTable.currentValue.results;
        // }
    }

}
