import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { QueryPrestoService } from 'src/app/core/services/queryPresto/query-presto-service.service';

@Injectable({
    providedIn: 'root'
})
export class CustomQueryResolver implements Resolve<Observable<string>> {

    constructor(
        private queryPrestoService: QueryPrestoService
    ) { }

    readonly databaseData: Array<string> = [
        'Database 1', 'Database 2', 'Database 3', 'Database 4'
    ]

    resolve(): Observable<any> {
        return this.queryPrestoService.getAvailableDbs().pipe(
            map(response => (
                { databaseData: response }
            )),
            catchError((error: any) => {
                // return of({ error: error })
                return of({ databaseData: this.databaseData }) // only for display purposes
            })
        )
    }
}
