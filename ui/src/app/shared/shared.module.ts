import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CardComponent } from './card/card.component';
import { CardModule } from 'primeng/card';
import { OverlayPanelTableComponent } from './overlay-panel-table/overlay-panel-table.component';
import { OverlayModule } from '@angular/cdk/overlay';
import { ButtonModule } from 'primeng/button';
import { ChipModule } from 'primeng/chip';
import { SelectMatchingColumnsComponent } from './select-matching-columns/select-matching-columns.component';
import { SelectColumnComponent } from './select-matching-columns/select-column/select-column.component';
import { PrivacyCheckComponent } from './tabs-content/privacy-check/privacy-check.component';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';


@NgModule({
    declarations: [
        CardComponent, 
        OverlayPanelTableComponent, 
        SelectMatchingColumnsComponent, 
        SelectColumnComponent, 
        PrivacyCheckComponent
    ],
    imports: [
        CommonModule, 
        CardModule, 
        OverlayModule, 
        ButtonModule, 
        ChipModule, 
        InputNumberModule, 
        FormsModule
    ],
    exports: [
        CardComponent, 
        OverlayPanelTableComponent, 
        SelectMatchingColumnsComponent, 
        PrivacyCheckComponent

    ],
    providers: []
})
export class SharedModule { }
