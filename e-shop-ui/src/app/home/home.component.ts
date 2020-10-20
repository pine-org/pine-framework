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
      Properties.builder('ID').build(),
      Properties.builder('Name').build(),
      Properties.builder('Description').build(),
      Properties.builder('Price').build()
    ];
  }

  getService(): Service<any> {
    return this.service;
  }
}
