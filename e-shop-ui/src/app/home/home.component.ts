import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product/product.service";
import {Properties, Text} from "../component/Properties";
import {Service} from "../service/AbstractService";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    columns = [
      Properties.builder(Text.builder('ID').build()).field("id").build(),
      Properties.builder(Text.builder('Name').build()).field("name").type("text").build(),
      Properties.builder(Text.builder('Code').build()).field("code").type("text").build(),
      Properties.builder(Text.builder('Price').build()).field("price").type("text").build(),
      Properties.builder(Text.builder('Description').build()).field("description").type("text").build(),
      Properties.builder(Text.builder('Photo').build()).field("photo").type("picture").build(),
      Properties.builder(Text.builder('Album').build()).field("photos").type("picture").build(),
    ];

  bunches: string[] = ['10', '15'];


  constructor(private service: ProductService) {
  }

  ngOnInit() {
  }

  getTitle(): string {
    return "PRODUCT";
  }

  getService(): Service<any> {
    return this.service;
    }

}
