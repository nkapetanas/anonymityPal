import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomQueryComponent } from './custom-query.component';
import { CustomQueryResolver } from './services/custom-query.resolver';

const routes: Routes = [
    { path: '', component: CustomQueryComponent, resolve: { resolver: CustomQueryResolver } }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CustomQueryRoutingModule { }
