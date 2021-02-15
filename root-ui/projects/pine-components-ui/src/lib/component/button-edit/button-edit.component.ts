import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-edit',
  templateUrl: './button-edit.component.html',
  styleUrls: ['./button-edit.component.css']
})
export class ButtonEditComponent extends AbstractButton {

  constructor() {
    super('Edit', 'fa fa-edit');
    this.color = 'btn-primary';
  }
}
