import {Component, OnInit} from '@angular/core';
import {GoodsService} from "../goods/goods.service";
import {Properties} from "../component/Properties";
import {Service} from "../service/AbstractService";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private service: GoodsService) {
  }

  ngOnInit() {
  }

  getTitle(): string {
    return "GOODS";
  }

  getColumns(): Properties[] {
    return [
      Properties.builder('ID').field("id").build(),
      Properties.builder('Name').field("name").build(),
      Properties.builder('Code').field("code").build(),
      Properties.builder('Price').field("price").build(),
      Properties.builder('Description').field("description").build(),
    ];
  }

  getService(): Service<any> {
    return this.service;
  }
}
