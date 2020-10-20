import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-read-button',
  templateUrl: './read-button.component.html',
  styleUrls: ['./read-button.component.css']
})
export class ReadButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Read').icon(Icon.iconOf('fa fa-eye')).build();

  constructor() {
  }

  ngOnInit() {
  }

}
