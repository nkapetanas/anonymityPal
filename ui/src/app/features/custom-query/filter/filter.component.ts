import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

  showFilterAdd: boolean = false;
  columns: Array<any> = [
    'Name', 'Id', 'Age'
  ]
  constructor() { }

  ngOnInit(): void {
  }

  addFilter() {
    this.showFilterAdd = true;
  }

}
