import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-refresh',
  templateUrl: './button-refresh.component.html',
  styleUrls: ['./button-refresh.component.css']
})
export class ButtonRefreshComponent extends AbstractButton {

  constructor() {
    super('Refresh', 'fa fa-recycle');
    this.color = 'btn-primary';
  }
}
