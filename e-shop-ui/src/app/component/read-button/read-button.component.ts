import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-read-button',
  templateUrl: './read-button.component.html',
  styleUrls: ['./read-button.component.css']
})
export class ReadButtonComponent extends AbstractButton {

  constructor() {
    super('Read', 'fa fa-eye');
  }
}
