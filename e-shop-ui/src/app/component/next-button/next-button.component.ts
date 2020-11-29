import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
    selector: 'app-next-button',
    templateUrl: './next-button.component.html',
    styleUrls: ['./next-button.component.css']
})
export class NextButtonComponent extends AbstractButton {

    constructor() {
        super('Next', 'fa fa-angle-double-right');
        this.color = '';
        this.shape = 'rounded-0';
    }
}
