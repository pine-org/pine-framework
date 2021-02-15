import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-next',
  templateUrl: './button-next.component.html',
  styleUrls: ['./button-next.component.css']
})
export class ButtonNextComponent extends AbstractButton {

  constructor() {
    super('Next', 'fa fa-angle-double-right');
    this.color = '';
    this.shape = 'rounded-0';
  }
}
