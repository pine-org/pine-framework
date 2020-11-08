import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties, Text} from "../Properties";

@Component({
  selector: 'app-delete-button',
  templateUrl: './delete-button.component.html',
  styleUrls: ['./delete-button.component.css']
})
export class DeleteButtonComponent implements OnInit {

  private hiddenText: boolean = false;

  private hiddenIcon: boolean = false;

  @Input() properties: Properties = Properties.builder(Text.builder('Delete').hidden(this.hiddenText).build())
    .icon(Icon.builder('fa fa-trash').hidden(this.hiddenIcon).build())
    .build();

  @Input() identities: any[] = [];

  @Input() click: (...identity: any[]) => void

  private _bunch: boolean = false;

  get bunch(): boolean {
    return this._bunch;
  }

  @Input()
  set bunch(value: boolean) {
    this._bunch = value;
    this.properties = Properties.builder(Text.builder('Delete Bunch').hidden(this.hiddenText).build())
      .icon(Icon.builder('fa fa-trash').hidden(this.hiddenIcon).build())
      .build();
  }

  constructor() {
  }

  ngOnInit() {
  }

}
