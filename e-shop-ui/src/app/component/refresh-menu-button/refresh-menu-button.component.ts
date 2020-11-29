import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
    selector: 'app-refresh-menu-button',
    templateUrl: './refresh-menu-button.component.html',
    styleUrls: ['./refresh-menu-button.component.css']
})
export class RefreshMenuButtonComponent extends AbstractButton {

    constructor() {
        super('Refresh', 'fa fa-recycle');
        this.color = 'btn-dark';
        this.shape = 'rounded-0';
        this.border = 'border-1';
    }
}
