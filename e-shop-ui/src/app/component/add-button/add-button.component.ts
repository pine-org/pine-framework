import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-add-button',
  templateUrl: './add-button.component.html',
  styleUrls: ['./add-button.component.css']
})
export class AddButtonComponent extends AbstractButton {

  constructor() {
    super('Add', 'fa fa-plus-square');
  }
}
