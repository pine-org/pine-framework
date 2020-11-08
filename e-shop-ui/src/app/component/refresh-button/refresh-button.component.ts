import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'app-refresh-button',
  templateUrl: './refresh-button.component.html',
  styleUrls: ['./refresh-button.component.css']
})
export class RefreshButtonComponent extends AbstractButton {

  constructor() {
    super('Refresh', 'fa fa-recycle');
  }
}
