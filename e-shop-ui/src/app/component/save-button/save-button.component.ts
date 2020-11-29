import {Component} from '@angular/core';
import {AbstractButton} from "../AbstractButton";

@Component({
    selector: 'app-save-button',
    templateUrl: './save-button.component.html',
    styleUrls: ['./save-button.component.css']
})
export class SaveButtonComponent extends AbstractButton {

    constructor() {
        super('Save', 'fa fa-save');
        this.color = 'btn-primary';
    }
}
