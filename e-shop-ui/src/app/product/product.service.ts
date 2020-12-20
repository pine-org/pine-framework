import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AbstractService} from "../service/AbstractService";
import {Product} from "./Product";

@Injectable({
  providedIn: 'root'
})
export class ProductService extends AbstractService<Product> {

  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  getUri(v = null) {
    return v == null ? "http://localhost:9091/e-shop/api/v1/product" :
      "http://localhost:9091/e-shop/api/v1/product" + v;
  }
}
