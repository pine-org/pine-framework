import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-refresh-menu-button',
  templateUrl: './refresh-menu-button.component.html',
  styleUrls: ['./refresh-menu-button.component.css']
})
export class RefreshMenuButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Refresh').icon(Icon.iconOf('fa fa-recycle')).build();

  @Input() click: () => void;

  constructor() {
  }

  ngOnInit() {
  }

}
