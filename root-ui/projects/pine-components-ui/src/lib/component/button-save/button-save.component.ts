import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-save',
  templateUrl: './button-save.component.html',
  styleUrls: ['./button-save.component.css']
})
export class ButtonSaveComponent extends AbstractButton {

  constructor() {
    super('Save', 'fa fa-save');
    this.color = 'btn-primary';
  }
}
