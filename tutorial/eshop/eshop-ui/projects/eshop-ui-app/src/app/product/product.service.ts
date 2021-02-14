import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "./Product";
import {AbstractService} from "pine-components-ui/lib/service/AbstractService";

@Injectable({
  providedIn: 'root'
})
export class ProductService extends AbstractService<Product> {

  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  getUri(v = null) {
    return v == null ? "http://localhost:9091/eshop/api/v1/product" :
      "http://localhost:9091/eshop/api/v1/product" + v;
  }
}
