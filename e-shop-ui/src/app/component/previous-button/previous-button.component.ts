import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-previous-button',
  templateUrl: './previous-button.component.html',
  styleUrls: ['./previous-button.component.css']
})
export class PreviousButtonComponent implements OnInit {
  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Previous').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-angle-double-left').hidden(this.hiddenIcon).build())
    .build();

  @Input() click: () => void;

  constructor() {
  }

  ngOnInit() {
  }

}
