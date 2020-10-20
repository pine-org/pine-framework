import {async, TestBed} from '@angular/core/testing';

import {GoodsService} from './goods.service';
import {Goods} from "./Goods";
import {HttpClientModule} from "@angular/common/http";
import {Service} from "../service/AbstractService";
import {GoodsList} from "./GoodsList";

describe('GoodsService', () => {
  let service: Service<Goods>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [GoodsService]
    });
    service = TestBed.get(GoodsService);
  });

  const countries = [
    'USA',
    'Germany',
    'Iran'
  ];

  const products = [
    'Ball',
    'Sunglasses',
    'Pen',
    'Pot',
    'Bicycle',
    'Laptop',
    'Bag',
    'Rug',
    'Glass',
  ];

  function getItem(a: any[]) {
    return a[Math.floor(Math.random() * a.length)];
  }

  it('GIVEN an object WHEN it is sent over the POST method THEN it should be save in database', async(() => {
    const data = {
      'name': getItem(products),
      'description': 'Made in ' + getItem(countries),
      'price': 1 + Math.floor(Math.random() * 100)
    };

    service.create(data).subscribe((response: Goods) => {
      expect(response).not.toBeNaN();
      expect(response._links.self.href).not.toBeNull();
      expect(response.name).not.toBeNull();
      expect(response.name).toEqual(data.name);
      expect(response.description).not.toBeNull();
      expect(response.description).toEqual(data.description);
      expect(response.price).not.toBeNull();
      expect(response.price).toEqual(data.price);
      expect(response.createBy).not.toBeNull();
      expect(response.createDate).not.toBeNull();
    });
  }));

  it('WHEN a request is sent over the GET verb THEN it should returns the first page of data', async(() => {
    service.list({page: '0', size: '10'}).subscribe((response: GoodsList) => {
      expect(response).not.toBeNaN();
      expect(response._embedded.data).not.toBeNaN();
      expect(response._embedded.data.length).toBeGreaterThanOrEqual(0);
    });
  }));

});
