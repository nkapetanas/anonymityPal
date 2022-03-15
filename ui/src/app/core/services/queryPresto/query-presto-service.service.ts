import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QueryPrestoService {

  constructor(private http: HttpClient) {
  }

  getResults(query: string): Observable<any> {
    return this.http.get<any>('http://localhost:8000/api/presto/getQueryResults', {
      params: new HttpParams().set('query', query),
    });
  }

  getAvailableDbs(): Observable<string> {
    return this.http.get<any>('http://localhost:8000/api/presto/getAvailableDbs');
  }

  getAvailableDbSchemas(selectedDB: string): Observable<string> {
    return this.http.get<any>('http://localhost:8000/api/presto/getAvailableDbSchemas', {
      params: new HttpParams().set('selectedDB', selectedDB),
    });
  }

  getAvailableSchemaTables(schema: string): Observable<string> {
    return this.http.get<any>('http://localhost:8000/api/presto/getAvailableSchemaTables', {
      params: new HttpParams().set('schema', schema),
    });
  }

  getColumnsFromTable(selectedTable: string): Observable<string> {
    return this.http.get<any>('http://localhost:8000/api/presto/getColumnsFromTable', {
      params: new HttpParams().set('selectedTable', selectedTable),
    });
  }
}
