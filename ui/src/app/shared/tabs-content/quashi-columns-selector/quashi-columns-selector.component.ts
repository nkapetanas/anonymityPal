import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-quashi-columns-selector',
    templateUrl: './quashi-columns-selector.component.html',
    styleUrls: ['./quashi-columns-selector.component.scss']
})
export class QuashiColumnsSelectorComponent implements OnInit {

    selectedColumns3: any[] = [];
    columns: any[] = []; // TODO: type of array

    constructor() { }

    ngOnInit(): void {
        this.columns = [
            { name: 'Column 1', code: 'C1' },
            { name: 'Column 2', code: 'C2' },
            { name: 'Column 3', code: 'C3' },
            { name: 'Column 4', code: 'C4' },
            { name: 'Column 5', code: 'C5' }
        ];
    }

    getPrivacyCheck() {
        
    }

}
