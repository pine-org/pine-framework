import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-read',
  templateUrl: './button-read.component.html',
  styleUrls: ['./button-read.component.css']
})
export class ButtonReadComponent extends AbstractButton {

  constructor() {
    super('Read', 'fa fa-eye');
    this.color = 'btn-info';
  }
}
