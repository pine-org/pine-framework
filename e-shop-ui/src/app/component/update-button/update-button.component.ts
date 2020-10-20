import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-update-button',
  templateUrl: './update-button.component.html',
  styleUrls: ['./update-button.component.css']
})
export class UpdateButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Update').icon(Icon.iconOf('fa fa-save')).build();

  constructor() {
  }

  ngOnInit() {
  }

}
