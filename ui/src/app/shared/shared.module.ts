import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CardComponent } from './card/card.component';
import {CardModule} from 'primeng/card';

@NgModule({
    declarations: [CardComponent],
    imports: [CommonModule, CardModule],
    exports: [CardComponent],
    providers: []
})
export class SharedModule { }
