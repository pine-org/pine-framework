import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-refresh-menu-button',
  templateUrl: './refresh-menu-button.component.html',
  styleUrls: ['./refresh-menu-button.component.css']
})
export class RefreshMenuButtonComponent implements OnInit {

  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Refresh').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-recycle').hidden(this.hiddenIcon).build())
    .build();

  @Input() click: () => void;

  constructor() {
  }

  ngOnInit() {
  }

}
