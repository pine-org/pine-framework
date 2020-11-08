import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-edit-button',
  templateUrl: './edit-button.component.html',
  styleUrls: ['./edit-button.component.css']
})
export class EditButtonComponent extends AbstractButton {

  constructor() {
    super('Edit', 'fa fa-edit');
  }
}
