import {Component, Input} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-delete-button',
  templateUrl: './delete-button.component.html',
  styleUrls: ['./delete-button.component.css']
})
export class DeleteButtonComponent extends AbstractButton {

  @Input() identity: any;


  constructor() {
    super('Delete', 'fa fa-trash');
    this.color = 'btn-danger';
  }

}
