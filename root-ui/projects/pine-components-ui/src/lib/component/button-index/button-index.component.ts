import {Component, Input, OnInit} from '@angular/core';
import {Properties} from "../Properties";
import {Text} from "../Text";

@Component({
  selector: 'pine-button-index',
  templateUrl: './button-index.component.html',
  styleUrls: ['./button-index.component.css']
})
export class ButtonIndexComponent implements OnInit {

  @Input() properties: Properties;

  @Input() color: string = '';

  @Input() border: string = 'border';

  @Input() shape: string = 'rounded-0';

  @Input() click: (idx: any) => void;

  constructor() {
  }

  private _index: number = 0;

  get index(): number {
    return this._index;
  }

  @Input()
  set index(value: number) {
    this._index = value;
    this.properties = Properties.builder(Text.of((this._index + 1).toString())).build();
  }

  ngOnInit() {
  }
}
