import {Component, Input} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-slide-button',
  templateUrl: './slide-button.component.html',
  styleUrls: ['./slide-button.component.css']
})
export class SlideButtonComponent extends AbstractButton {

  @Input() modal: any;

  constructor() {
    super('Slide', 'fa fa-file-image');
    this.color = 'btn-info';
  }

}
