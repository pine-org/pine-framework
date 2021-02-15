import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-add',
  templateUrl: './button-add.component.html',
  styleUrls: ['./button-add.component.css']
})
export class ButtonAddComponent extends AbstractButton {

  constructor() {
    super('Add', 'fa fa-plus-square');
    this.color = 'btn-primary';
  }
}
