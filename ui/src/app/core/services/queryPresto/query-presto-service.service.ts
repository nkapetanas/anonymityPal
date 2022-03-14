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
    const params = new HttpParams().append('query', query);
    return this.http.get<any>('http://localhost:8000/api/presto/getQueryResults', {params});
  }

  getAvailableDbs(): Observable<string> {
    return this.http.get<any>('http://localhost:8000/api/presto/getAvailableDbs');
  }

  getAvailableDbSchemas(selectedDB: string): Observable<string> {
    const params = new HttpParams().append('selectedDB', selectedDB);
    return this.http.get<any>('http://localhost:8000/api/presto/getAvailableDbSchemas', {params});
  }

  getAvailableSchemaTables(schema: string): Observable<string> {
    const params = new HttpParams().append('schema', schema);
    return this.http.get<any>('http://localhost:8000/api/presto/getAvailableSchemaTables', {params});
  }
}
