import {async, TestBed} from '@angular/core/testing';

import {GoodsService} from './goods.service';
import {Goods} from "./Goods";
import {HttpClientModule} from "@angular/common/http";
import {Service} from "../service/AbstractService";
import {Page} from "../service/Page";

describe('Goods Service Tests', () => {
  let service: Service<Goods>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [GoodsService]
    });
    service = TestBed.get(GoodsService);
  });

  it('GIVEN an object WHEN it is sent over the POST method THEN it should be save in database', async(() => {
    const data = {
      'name': 'Laptop',
      'code': '001',
      'description': 'Made in the USA',
      'price': 1450
    };

    service.create(data).subscribe((response: Goods) => {
      expect(response).not.toBeNaN();
      expect(response._links.href).not.toBeNull();
      expect(response.name).not.toBeNull();
      expect(response.name).toEqual(data.name);
      expect(response.code).not.toBeNull();
      expect(response.code).toEqual(data.code);
      expect(response.description).not.toBeNull();
      expect(response.description).toEqual(data.description);
      expect(response.price).not.toBeNull();
      expect(response.price).toEqual(data.price);
      expect(response.createBy).not.toBeNull();
      expect(response.createDate).not.toBeNull();
    });
  }));

  it('WHEN a request is sent over the GET verb THEN it should returns the first page of data', async(() => {
    service.list(new Page()).subscribe((response: Page) => {
      expect(response).not.toBeNaN();
      expect(response.content).not.toBeNaN();
      expect(response.content.length).toBeGreaterThanOrEqual(0);
    });
  }));

});
