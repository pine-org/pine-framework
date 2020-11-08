import {Directive, Input, OnInit} from "@angular/core";
import {Icon, Properties, Text} from "./Properties";

@Directive()
export abstract class AbstractButton implements OnInit {

  text: string = '';

  iconName: string = '';
  @Input() properties: Properties = Properties.builder(Text.builder(this.text).hidden(this.hiddenText).build())
    .icon(Icon.builder(this.iconName).hidden(this.hiddenIcon).build())
    .build();
  @Input() click: (...params: any[]) => void

  protected constructor(text: string, iconName: string) {
    this.text = text;
    this.iconName = iconName;
  }

  private _hiddenText: boolean = false;

  get hiddenText(): boolean {
    return this._hiddenText;
  }

  @Input()
  set hiddenText(value: boolean) {
    this._hiddenText = value;
    this.properties = Properties.builder(Text.builder(this.text).hidden(this._hiddenText).build())
      .icon(Icon.builder(this.iconName).hidden(this._hiddenIcon).build())
      .build();
  }

  private _hiddenIcon: boolean = false;

  get hiddenIcon(): boolean {
    return this._hiddenIcon;
  }

  @Input()
  set hiddenIcon(value: boolean) {
    this._hiddenIcon = value;
    this.properties = Properties.builder(Text.builder(this.text).hidden(this._hiddenText).build())
      .icon(Icon.builder(this.iconName).hidden(this._hiddenIcon).build())
      .build();
  }

  ngOnInit() {
  }
}
