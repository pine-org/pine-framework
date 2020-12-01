import {Component, Input} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-coffee-button',
  templateUrl: './coffee-button.component.html',
  styleUrls: ['./coffee-button.component.css']
})
export class CoffeeButtonComponent extends AbstractButton {

  @Input() modal: any;

  constructor() {
    super('Make Coffee', 'fa fa-coffee');
    this.color = 'btn-info';
  }
}
