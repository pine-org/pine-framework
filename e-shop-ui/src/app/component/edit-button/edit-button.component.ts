import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-edit-button',
  templateUrl: './edit-button.component.html',
  styleUrls: ['./edit-button.component.css']
})
export class EditButtonComponent implements OnInit {

  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Edit').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-edit').hidden(this.hiddenIcon).build())
    .build();

  constructor() {
  }

  ngOnInit() {
  }

}
