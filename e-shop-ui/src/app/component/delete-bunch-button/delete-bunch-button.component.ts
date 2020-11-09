import {Component, Input} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-delete-bunch-button',
  templateUrl: './delete-bunch-button.component.html',
  styleUrls: ['./delete-bunch-button.component.css']
})
export class DeleteBunchButtonComponent extends AbstractButton {

  @Input() identities: any[] = [];

  constructor() {
    super('Delete Bunch', 'fa fa-trash');
    this.color = 'btn-danger';
  }

}
