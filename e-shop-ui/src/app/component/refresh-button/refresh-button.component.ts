import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-refresh-button',
  templateUrl: './refresh-button.component.html',
  styleUrls: ['./refresh-button.component.css']
})
export class RefreshButtonComponent implements OnInit {
  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Refresh').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-recycle').hidden(this.hiddenIcon).build())
    .build();

  constructor() {
  }

  ngOnInit() {
  }

}
