import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
    { path: '', redirectTo: 'privacy-buddy', pathMatch: 'full' },
    { path: 'index.jsp', redirectTo: 'privacy-buddy' },
    {
        path: 'privacy-buddy', loadChildren: () =>
            import('./features/home/home-routing.module')
                .then(m => m.HomeRoutingModule)
    },
    {
        path: 'privacy-buddy/create-question', loadChildren: () =>
            import('./features/query-cards-selectors/query-cards-selectors-routing.module')
                .then(m => m.QueryCardsSelectorsRoutingModule)
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes,
        {
            useHash: true
        })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
