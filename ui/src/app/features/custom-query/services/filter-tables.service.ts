import { Injectable } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { Subject } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class FilterTablesService {

    getFilterTables$ = new Subject<Map<string, SelectItem[]>>();

    constructor() { }

    setFilterTables(value: Map<string, SelectItem[]>) {
        this.getFilterTables$.next(value);
    }

}
