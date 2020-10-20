import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-previous-button',
  templateUrl: './previous-button.component.html',
  styleUrls: ['./previous-button.component.css']
})
export class PreviousButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Previous').icon(Icon.iconOf("fa fa-angle-double-left")).build();

  @Input() click: () => void;

  constructor() {
  }

  ngOnInit() {
  }

}
