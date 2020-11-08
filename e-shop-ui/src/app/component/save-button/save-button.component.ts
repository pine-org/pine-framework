import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";


@Component({
  selector: 'app-save-button',
  templateUrl: './save-button.component.html',
  styleUrls: ['./save-button.component.css']
})
export class SaveButtonComponent implements OnInit {

  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Save').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-save').hidden(this.hiddenIcon).build())
    .build();

  constructor() {
  }

  ngOnInit() {
  }

}
