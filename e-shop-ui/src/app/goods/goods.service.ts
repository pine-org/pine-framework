import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AbstractService} from "../service/AbstractService";
import {Goods} from "./Goods";

@Injectable({
  providedIn: 'root'
})
export class GoodsService extends AbstractService<Goods> {

  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  getUri(v = null) {
    return v == null ? "http://localhost:9091/e-shop/api/v1/goods/search/page/" :
      "http://localhost:9091/e-shop/api/v1/goods/search/page/" + v;
  }
}
