import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-edit-button',
  templateUrl: './edit-button.component.html',
  styleUrls: ['./edit-button.component.css']
})
export class EditButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Edit').icon(Icon.iconOf('fa fa-edit')).build();

  constructor() {
  }

  ngOnInit() {
  }

}
