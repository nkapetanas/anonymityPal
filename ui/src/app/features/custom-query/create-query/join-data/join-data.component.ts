import { Component, OnInit, Input } from '@angular/core';
import { SelectItem } from 'primeng/api';

@Component({
    selector: 'app-join-data',
    templateUrl: './join-data.component.html',
    styleUrls: ['./join-data.component.scss']
})
export class JoinDataComponent implements OnInit {

    @Input() selectedTable: string;
    @Input() databaseOptions: Array<SelectItem> = [];

    showColumnsModal: boolean = false;
    selectedTableJoin: string = '';
    joinedTable: string = '';
    selectedColumn: string = '';
    selectedColumnJoin: string = '';
    columnsOptions: Array<SelectItem> = [];
    columnsJoinOptions: Array<SelectItem> = [];
    selectedColumns: string = '';

    constructor() { }

    ngOnInit(): void {
    }

    removeFinalTable() {
        this.joinedTable = '';
        this.removeSelectedColumns();
    }

    matchColumns() {
        this.showColumnsModal = true;
    }

    saveSelectedColumns() {
        this.selectedColumns = this.selectedColumn + ' = ' + this.selectedColumnJoin;
        this.showColumnsModal = false;
    }

    closeColumnsModal() {
        this.selectedColumn = '';
        this.selectedColumnJoin = '';
    }

    removeSelectedColumns() {
        this.selectedColumns = '';
    }

    getSelectedTable(value: string) {
        this.selectedTableJoin = value;
    }

}
