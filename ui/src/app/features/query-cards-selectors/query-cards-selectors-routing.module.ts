import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QueryCardsSelectorsComponent } from './query-cards-selectors.component';

const routes: Routes = [
    { path: '', component: QueryCardsSelectorsComponent },
    {
        path: 'custom-query', loadChildren: () =>
            import('../custom-query/custom-query-routing.module')
                .then(m => m.CustomQueryRoutingModule)
    },
    {
        path: 'native-query', loadChildren: () =>
            import('../native-query/native-query-routing.module')
                .then(m => m.NativeQueryRoutingModule)
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class QueryCardsSelectorsRoutingModule { }
