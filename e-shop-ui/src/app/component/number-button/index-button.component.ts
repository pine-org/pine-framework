import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-index-button',
  templateUrl: './index-button.component.html',
  styleUrls: ['./index-button.component.css']
})
export class IndexButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Number').icon(Icon.iconOf('')).build();
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
    this.properties = Properties.builder((this._number + 1).toString()).icon(Icon.iconOf('')).build();
  }

  ngOnInit() {
  }

}
