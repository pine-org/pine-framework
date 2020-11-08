import {Component, Input, OnInit} from '@angular/core';
import {Properties, Text} from "../Properties";

@Component({
  selector: 'app-index-button',
  templateUrl: './index-button.component.html',
  styleUrls: ['./index-button.component.css']
})
export class IndexButtonComponent implements OnInit {

  @Input() properties;

  @Input() color: string = '';

  @Input() click: (idx: any) => void;

  private _index: number = 0;

  constructor() {
  }

  ngOnInit() {
  }

  get index(): number {
    return this._index;
  }

  @Input()
  set index(value: number) {
    this._index = value;
    this.properties = Properties.builder(Text.of((this._index + 1).toString())).build();
  }
}
