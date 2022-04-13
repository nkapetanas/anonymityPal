import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CardComponent } from './card/card.component';
import {CardModule} from 'primeng/card';
import { OverlayPanelTableComponent } from './overlay-panel-table/overlay-panel-table.component';
import {OverlayModule} from '@angular/cdk/overlay';
import { ButtonModule } from 'primeng/button';
import { ChipModule } from 'primeng/chip';

@NgModule({
    declarations: [CardComponent, OverlayPanelTableComponent],
    imports: [CommonModule, CardModule, OverlayModule, ButtonModule, ChipModule],
    exports: [CardComponent, OverlayPanelTableComponent],
    providers: []
})
export class SharedModule { }
