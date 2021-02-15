import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
  selector: 'pine-button-refresh-menu',
  templateUrl: './button-refresh-menu.component.html',
  styleUrls: ['./button-refresh-menu.component.css']
})
export class ButtonRefreshMenuComponent extends AbstractButton {

  constructor() {
    super('Refresh', 'fa fa-recycle');
    this.color = 'btn-dark';
    this.shape = 'rounded-0';
    this.border = 'border-1';
  }
}
