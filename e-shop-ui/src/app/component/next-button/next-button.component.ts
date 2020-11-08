import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-next-button',
  templateUrl: './next-button.component.html',
  styleUrls: ['./next-button.component.css']
})
export class NextButtonComponent implements OnInit {
  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Next').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-angle-double-right').hidden(this.hiddenIcon).build())
    .build();

  @Input() click: () => void;

  constructor() {
  }

  ngOnInit() {
  }

}
