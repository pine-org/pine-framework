import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
    selector: 'app-previous-button',
    templateUrl: './previous-button.component.html',
    styleUrls: ['./previous-button.component.css']
})
export class PreviousButtonComponent extends AbstractButton {

    constructor() {
        super('Previous', 'fa fa-angle-double-left');
        this.color = '';
        this.shape = 'rounded-0';
    }
}
