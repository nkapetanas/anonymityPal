import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CardComponent } from './card/card.component';
import {CardModule} from 'primeng/card';
import { OverlayPanelTableComponent } from './overlay-panel-table/overlay-panel-table.component';
import {OverlayModule} from '@angular/cdk/overlay';
import { ButtonModule } from 'primeng/button';
import { ChipModule } from 'primeng/chip';
import { SelectMatchingColumnsComponent } from './select-matching-columns/select-matching-columns.component';

@NgModule({
    declarations: [CardComponent, OverlayPanelTableComponent, SelectMatchingColumnsComponent],
    imports: [CommonModule, CardModule, OverlayModule, ButtonModule, ChipModule],
    exports: [CardComponent, OverlayPanelTableComponent, SelectMatchingColumnsComponent],
    providers: []
})
export class SharedModule { }
