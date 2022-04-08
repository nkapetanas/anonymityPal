import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-tabs-content',
  templateUrl: './tabs-content.component.html',
  styleUrls: ['./tabs-content.component.scss']
})
export class TabsContentComponent implements OnInit {

  @Input() data : any;
  constructor() { }

  ngOnInit(): void {
  }

}
