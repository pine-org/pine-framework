import {Component, Input} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-photo',
  templateUrl: './button-photo.component.html',
  styleUrls: ['./button-photo.component.css']
})
export class ButtonPhotoComponent extends AbstractButton {

  @Input() modal: any;

  constructor() {
    super('Slide', 'fa fa-file-image');
    this.color = 'btn-info';
  }

}
