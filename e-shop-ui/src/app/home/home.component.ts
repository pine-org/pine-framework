import {Component, OnInit} from '@angular/core';
import {GoodsService} from "../goods/goods.service";
import {Properties, Text} from "../component/Properties";
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

  columns = [
    Properties.builder(Text.builder('ID').build()).field("id").build(),
    Properties.builder(Text.builder('Name').build()).field("name").build(),
    Properties.builder(Text.builder('Code').build()).field("code").build(),
    Properties.builder(Text.builder('Price').build()).field("price").build(),
    Properties.builder(Text.builder('Description').build()).field("description").build(),
  ];

  bunches: string[] = ['2'];

  getService(): Service<any> {
    return this.service;
  }

}
