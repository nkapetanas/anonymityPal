import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Consts } from "../../../consts";
import { CustomQueryParams } from 'src/app/features/custom-query/model/CustomQueryParams';

@Injectable({
    providedIn: 'root'
})
export class QueryPrestoService {

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    constructor(private http: HttpClient) {
    }

    getResults(query: string): Observable<any> {
        return this.http.get<any>(Consts.API_PATH + Consts.API_GET_QUERY_RESULTS, {
            params: new HttpParams().set('query', query),
        });
    }

    getCustomQueryResults(query: CustomQueryParams) {
        debugger;
        return this.http.post<any>(Consts.API_PATH + Consts.API_GET_CUSTOM_QUERY_RESULTS, query, this.httpOptions);
    }

    getAvailableDbs(): Observable<string> {
        return this.http.get<any>(Consts.API_PATH + Consts.API_GET_AVAILABLE_DBS);
    }

    getAvailableDbSchemas(selectedDB: string): Observable<string> {
        return this.http.get<any>(Consts.API_PATH + Consts.API_GET_AVAILABLE_DB_SCHEMAS, {
            params: new HttpParams().set('selectedDB', selectedDB),
        });
    }

    getAvailableSchemaTables(schema: string): Observable<string> {
        return this.http.get<any>(Consts.API_PATH + Consts.API_GET_AVAILABLE_SCHEMA_TABLES, {
            params: new HttpParams().set('schema', schema),
        });
    }

    getColumnsFromTable(selectedTable: string): Observable<string> {
        return this.http.get<any>(Consts.API_PATH + Consts.API_GET_COLUMNS_FROM_TABLE, {
            params: new HttpParams().set('selectedTable', selectedTable),
        });
    }

    getFilterOperationOptions(): Observable<string> {
        return this.http.get<any>(Consts.API_PATH + Consts.API_FILTER_OPERATIONS);
    }

    getJoinOperationOptions(): Observable<string> {
        return this.http.get<any>(Consts.API_PATH + Consts.API_JOIN_OPERATIONS);
    }
}
