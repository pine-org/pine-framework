import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";


@Component({
  selector: 'app-save-button',
  templateUrl: './save-button.component.html',
  styleUrls: ['./save-button.component.css']
})
export class SaveButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Save').icon(Icon.iconOf('fa fa-save')).build();

  constructor() {
  }

  ngOnInit() {
  }

}
