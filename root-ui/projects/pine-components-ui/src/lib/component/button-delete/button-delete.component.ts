import {Component, Input} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-delete',
  templateUrl: './button-delete.component.html',
  styleUrls: ['./button-delete.component.css']
})
export class ButtonDeleteComponent extends AbstractButton {

  @Input() identity: any;

  constructor() {
    super('Delete', 'fa fa-trash');
    this.color = 'btn-danger';
  }

}
