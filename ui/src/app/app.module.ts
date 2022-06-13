import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { MenubarModule } from 'primeng/menubar';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatTabsModule } from '@angular/material/tabs';
import { MultiSelectModule } from 'primeng/multiselect';
import { MatListModule } from '@angular/material/list';
import { ListboxModule } from 'primeng/listbox';
import { PanelModule } from 'primeng/panel';
import { CardModule } from 'primeng/card';
import { QuashiColumnsSelectorComponent } from './shared/tabs-content/quashi-columns-selector/quashi-columns-selector.component';
import { DataTableComponent } from './shared/tabs-content/data-table/data-table.component';
import { StepsModule } from 'primeng/steps';
import { SharedModule } from './shared/shared.module';
import { QueryCardsSelectorsComponent } from './features/query-cards-selectors/query-cards-selectors.component';
import { CustomQueryComponent } from './features/custom-query/custom-query.component';
import { TabsContentComponent } from './shared/tabs-content/tabs-content.component';
import { HomeComponent } from './features/home/home.component';
import { NativeQueryComponent } from './features/native-query/native-query.component';
import { DropdownModule } from 'primeng/dropdown';
import { SelectButtonModule } from 'primeng/selectbutton';
import { DialogModule } from 'primeng/dialog';
import { ChipModule } from 'primeng/chip';
import { JoinDataComponent } from './features/custom-query/components/join-data/join-data.component';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { AccordionModule } from 'primeng/accordion';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { SortingComponent } from './features/custom-query/components/sorting/sorting.component';
import { RowLimitComponent } from './features/custom-query/components/row-limit/row-limit.component';
import {InputNumberModule} from 'primeng/inputnumber';
import { OverlayModule } from '@angular/cdk/overlay';
import { FilterComponent } from './features/custom-query/components/filter/filter.component';
@NgModule({
    declarations: [
        AppComponent,
        DataTableComponent,
        QuashiColumnsSelectorComponent,
        QueryCardsSelectorsComponent,
        CustomQueryComponent,
        TabsContentComponent,
        HomeComponent,
        NativeQueryComponent,
        JoinDataComponent,
        SortingComponent,
        RowLimitComponent,
        FilterComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        InputTextModule,
        ButtonModule,
        HttpClientModule,
        TableModule,
        MenubarModule,
        FormsModule,
        MatTabsModule,
        MultiSelectModule,
        MatListModule,
        ListboxModule,
        PanelModule,
        CardModule,
        StepsModule,
        SharedModule,
        DropdownModule,
        SelectButtonModule,
        DialogModule,
        ChipModule,
        OverlayPanelModule,
        AccordionModule,
        MessagesModule,
        MessageModule,
        InputNumberModule,
        OverlayModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
