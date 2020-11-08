import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-read-button',
  templateUrl: './read-button.component.html',
  styleUrls: ['./read-button.component.css']
})
export class ReadButtonComponent implements OnInit {

  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Read').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-eye').hidden(this.hiddenIcon).build())
    .build();

  constructor() {
  }

  ngOnInit() {
  }

}
