import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class QueryPrestoService {

    constructor(private http: HttpClient) { }

    getResults(query: string): Observable<any> {
        return this.http.get<any>('localhost:8080/api/presto/getQueryResults');
    }

    getAvailableDbs(): Observable<string> {
        return this.http.get<any>('localhost:8080/api/presto/getAvailableDbs');
    }

    getAvailableDbSchemas(selectedDB: string): Observable<string> {
        return this.http.get<any>('localhost:8080/api/presto/getAvailableDbSchemas');
    }

    getAvailableSchemaTables(schema: string): Observable<string> {
        return this.http.get<any>('localhost:8080/api/presto/getAvailableSchemaTables');
    }
}
