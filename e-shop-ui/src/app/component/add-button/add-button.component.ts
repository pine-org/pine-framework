import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-add-button',
  templateUrl: './add-button.component.html',
  styleUrls: ['./add-button.component.css']
})
export class AddButtonComponent implements OnInit {
  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Add').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-square').hidden(this.hiddenIcon).build())
    .build();

  constructor() {
  }

  ngOnInit() {
  }

}
