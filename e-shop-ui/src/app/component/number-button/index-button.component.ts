import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-index-button',
  templateUrl: './index-button.component.html',
  styleUrls: ['./index-button.component.css']
})
export class IndexButtonComponent implements OnInit {

  private hiddenText: boolean = false;

  private hiddenIcon: boolean = true;

  @Input() properties: Properties = Properties.builder(Text.builder('Number').hidden(this.hiddenText).build())
    .icon(Icon.builder('').hidden(this.hiddenIcon).build())
    .build();

  @Input() click: (args: any) => void;

  constructor() {
  }

  private _number: number = 0;

  get number(): number {
    return this._number;
  }

  @Input()
  set number(value: number) {
    this._number = value;
    this.properties = Properties.builder(Text.builder((this._number + 1).toString()).hidden(this.hiddenText).build())
      .icon(Icon.builder('').hidden(this.hiddenIcon).build())
      .build();
  }

  ngOnInit() {
  }

}
