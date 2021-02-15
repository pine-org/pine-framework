import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-update',
  templateUrl: './button-update.component.html',
  styleUrls: ['./button-update.component.css']
})
export class ButtonUpdateComponent extends AbstractButton {

  constructor() {
    super('Update', 'fa fa-save');
    this.color = 'btn-primary';
  }
}
