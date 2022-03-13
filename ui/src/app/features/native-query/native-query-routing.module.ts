import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NativeQueryComponent } from './native-query.component';

const routes: Routes = [
    { path: '', component: NativeQueryComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class NativeQueryRoutingModule { }
