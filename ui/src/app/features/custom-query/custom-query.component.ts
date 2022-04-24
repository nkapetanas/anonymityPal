import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {SelectItem} from 'primeng/api';
import {QueryPrestoService} from 'src/app/core/services/queryPresto/query-presto-service.service';
import {createDropdownOptions} from '../../core/utils/dropdown-options.helper'
import {CustomQueryParams} from './model/CustomQueryParams';
import {FilterOperation} from './model/FilterOperation';

@Component({
  selector: 'app-custom-query',
  templateUrl: './custom-query.component.html',
  styleUrls: ['./custom-query.component.scss']
})
export class CustomQueryComponent implements OnInit {

  selectedTable: string;
  databasesOptions: Array<SelectItem> = [];
  results: Array<any>;
  joinDataOption: Array<SelectItem> = [];
  joinDataValue: number[] = [];
  filterLists: Array<FilterOperation>;

  constructor(
    private route: ActivatedRoute,
    private queryPrestoService: QueryPrestoService) {
  }

  ngOnInit(): void {

    this.joinDataOption = [
      {icon: 'join-icons join-left-icon', label: 'Left outer join', value: 1},
      {icon: 'join-icons join-right-icon', label: 'Right outer join', value: 1},
      {icon: 'join-icons join-inner-icon', label: 'Inner join', value: 1},
    ];

    this.route.data.subscribe(data => {
      this.onRouteDataChange(data);
    })
  }

  execute() {
    const query: CustomQueryParams = {
      completeTablePath: this.selectedTable,
      isJoin: false,
      tableToJoinPathCatalog: '',
      joinOperator: '',
      joinTableColumnValues: [''],
      filterOperationsList: this.filterLists
    }

    this.queryPrestoService.getCustomQueryResults(query).subscribe(response => {
      console.log(response)
    });
  }

  private onRouteDataChange(data: any) {
    this.databasesOptions = createDropdownOptions(data.resolver.databaseData);
  }

  getSelectedTable(value: string) {
    this.selectedTable = value;
  }

  getFilterQuery(value: Array<FilterOperation>) {
    this.filterLists = value;
  }
}
