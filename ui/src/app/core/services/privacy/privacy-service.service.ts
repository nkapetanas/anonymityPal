import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { Observable } from 'rxjs';
import { Consts } from 'src/app/consts';
import { CustomQueryParams } from 'src/app/features/custom-query/model/CustomQueryParams';
import { PrivacyCheck } from 'src/app/shared/tabs-content/privacy-check/PrivacyCheck';
import { QueryResults } from 'src/app/shared/tabs-content/quashi-columns-selector/QueryResults.model';

@Injectable({
    providedIn: 'root'
})
export class PrivacyService {

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };
    
    constructor(private http: HttpClient) { }

    getResults(query: string): Observable<any> {
        return this.http.get<any>(Consts.API_PATH + Consts.API_GET_QUERY_RESULTS, {
            params: new HttpParams().set('query', query),
        });
    }

    getCustomQueryResults(query: CustomQueryParams) {
        return this.http.post<any>(Consts.API_PATH + Consts.API_GET_CUSTOM_QUERY_RESULTS, query, this.httpOptions);
    }

    checkPrivacyPreservation(data: QueryResults): Observable<any> {
        return this.http.post<any>(Consts.API_PATH + Consts.API_CHECK_PRIVACY_PRESENTATION, data, this.httpOptions);
    }

    getQueryResultsPrivacyChecked(data: PrivacyCheck) {
        let params = new HttpParams;
        if(!_.isNull(data.kAnonymityParam)) {
            params = params.set('kAnonymityParam', data.kAnonymityParam);
        }
        if(!_.isNull(data.lDiversityParam)) {
            params = params.set('lDiversityParam', data.lDiversityParam);
        }
        
        return this.http.post<any>(Consts.API_PATH + Consts.API_GET_QUERY_PRIVACY_RESULTS, { dbRecordList: data.dbRecordList }, { params });
    }
}
