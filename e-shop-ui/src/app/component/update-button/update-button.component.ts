import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-update-button',
  templateUrl: './update-button.component.html',
  styleUrls: ['./update-button.component.css']
})
export class UpdateButtonComponent extends AbstractButton {

  constructor() {
    super('Update', 'fa fa-save');
  }
}
