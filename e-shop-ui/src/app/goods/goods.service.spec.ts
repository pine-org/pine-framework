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

  it('create[SUCCESS]: WHEN send a request over the POST verb to save an object THEN return an ID', async(() => {
    const data = {
      'name': 'Laptop',
      'code': '001',
      'description': 'Made in the USA',
      'price': 1450
    };

    service.create(data).subscribe((response) => {
      expect(response).not.toBeNaN();
      expect(response.content).not.toBeNull();
      console.log('Goods Service Tests::create[SUCCESS]\n'
        + 'result\t=> It has been saved\n'
        + 'Object:\n'
        + JSON.stringify(data, null, 2) + '\n'
        + 'Response:\n'
        + JSON.stringify(response, null, 2) + '\n');
    });
  }));

  it('create[FAILED]: WHEN send a request over the POST verb to save an invalid object THEN return an exception', async(() => {
    const data = {
      'name': '',
      'code': '',
      'description': 'Made in the USA',
      'price': 1450
    };

    service.create(data).subscribe((response) => {
    }, error => {
      expect(error.error).not.toBeNull();
      expect(error.error.length).toBeGreaterThan(0);
      expect(error.error[0].message).not.toBeNull();
      expect(error.error[0].code).toEqual(422);
      expect(error.error[0].httpStatus).toEqual("UNPROCESSABLE_ENTITY");
      expect(error.error[1].message).not.toBeNull();
      expect(error.error[1].code).toEqual(422);
      expect(error.error[1].httpStatus).toEqual("UNPROCESSABLE_ENTITY");
      console.log('Goods Service Tests::create[FAILED]\n'
        + 'Result\t=> It has been failed\n'
        + 'Cause\t=> Validation error\n'
        + 'Object:\n'
        + JSON.stringify(data, null, 2) + '\n'
        + 'Error:\n'
        + JSON.stringify(error, null, 2) + '\n');
    });
  }));

  it('list[SUCCESS]: WHEN send a request over the GET verb to read data THEN return a page of data', async(() => {
    let page = new Page();
    service.list(page).subscribe((response: Page) => {
      expect(response).not.toBeNaN();
      expect(response.content).not.toBeNaN();
      expect(response.content.length).toBeGreaterThanOrEqual(0);
      console.log('Goods Service Tests::list[SUCCESS]\n'
        + 'result\t=> It has been loaded\n'
        + 'Object:\n'
        + JSON.stringify(page, null, 2) + '\n'
        + 'Response:\n'
        + JSON.stringify(response, null, 2) + '\n');
    });
  }));

});
