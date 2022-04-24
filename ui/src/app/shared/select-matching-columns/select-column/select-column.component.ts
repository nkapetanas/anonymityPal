import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { SelectItem } from 'primeng/api';

@Component({
  selector: 'app-select-column',
  templateUrl: './select-column.component.html',
  styleUrls: ['./select-column.component.scss']
})
export class SelectColumnComponent implements OnInit, OnChanges {

  @Input() columnOptions: Array<SelectItem> = [];
  @Input() label: string;
  
  isDataColumnCollapsed: boolean = false;
  tableValue: string = '';

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['label'] && changes['label'].currentValue) {
      this.tableValue = changes['label'].currentValue.split('.')[2]
    }
  }

  collapseColumns() {
    this.isDataColumnCollapsed = !this.isDataColumnCollapsed;
  }

}
