import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-previous',
  templateUrl: './button-previous.component.html',
  styleUrls: ['./button-previous.component.css']
})
export class ButtonPreviousComponent extends AbstractButton {

  constructor() {
    super('Previous', 'fa fa-angle-double-left');
    this.color = '';
    this.shape = 'rounded-0';
  }
}
