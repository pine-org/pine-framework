import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-next-button',
  templateUrl: './next-button.component.html',
  styleUrls: ['./next-button.component.css']
})
export class NextButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Next').icon(Icon.iconOf("fa fa-angle-double-right")).build();

  @Input() click: () => void;

  constructor() {
  }

  ngOnInit() {
  }

}
