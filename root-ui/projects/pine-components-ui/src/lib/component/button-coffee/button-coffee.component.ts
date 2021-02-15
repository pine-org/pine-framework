import {Component, Input} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-coffee',
  templateUrl: './button-coffee.component.html',
  styleUrls: ['./button-coffee.component.css']
})
export class ButtonCoffeeComponent extends AbstractButton {

  @Input() modal: any;

  constructor() {
    super('Make Coffee', 'fa fa-coffee');
    this.color = 'btn-info';
  }
}
