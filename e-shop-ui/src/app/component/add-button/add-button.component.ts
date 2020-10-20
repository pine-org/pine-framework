import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-add-button',
  templateUrl: './add-button.component.html',
  styleUrls: ['./add-button.component.css']
})
export class AddButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Add').icon(Icon.iconOf('fa fa-plus-square')).build();

  constructor() {
  }

  ngOnInit() {
  }

}
