import {Component, Input, OnInit} from '@angular/core';
import {Icon, Properties} from "../Properties";

@Component({
  selector: 'app-delete-button',
  templateUrl: './delete-button.component.html',
  styleUrls: ['./delete-button.component.css']
})
export class DeleteButtonComponent implements OnInit {

  @Input() properties: Properties = Properties.builder('Delete').icon(Icon.iconOf('fa fa-trash')).build();

  @Input() identities: any[] = [];

  @Input() click: (...identity: any[]) => void

  constructor() {
  }

  ngOnInit() {
  }

}
