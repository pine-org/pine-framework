import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-update-button',
  templateUrl: './update-button.component.html',
  styleUrls: ['./update-button.component.css']
})
export class UpdateButtonComponent implements OnInit {

  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Update').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-save').hidden(this.hiddenIcon).build())
    .build();

  constructor() {
  }

  ngOnInit() {
  }

}
