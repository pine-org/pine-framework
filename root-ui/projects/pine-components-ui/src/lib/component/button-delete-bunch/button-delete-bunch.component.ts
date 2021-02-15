import {Component, Input} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-delete-bunch',
  templateUrl: './button-delete-bunch.component.html',
  styleUrls: ['./button-delete-bunch.component.css']
})
export class ButtonDeleteBunchComponent extends AbstractButton {

  @Input() identities: any[] = [];

  constructor() {
    super('Delete Bunch', 'fa fa-trash');
    this.color = 'btn-danger';
  }

}
