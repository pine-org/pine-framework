import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-refresh-button',
  templateUrl: './refresh-button.component.html',
  styleUrls: ['./refresh-button.component.css']
})
export class RefreshButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Refresh').icon(Icon.iconOf('fa fa-recycle')).build();

  constructor() {
  }

  ngOnInit() {
  }

}
