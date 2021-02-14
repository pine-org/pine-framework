import {Page} from "./Page";
import {Observable} from "rxjs";

export interface Service<T> {
  getUri(v): string;

  list(page: Page): Observable<Page>;

  create(obj): Observable<any>;

  read(id): Observable<T>;

  update(id, obj): Observable<any>;

  delete(id): Observable<any>;

  deleteAll(...identities: any[]): Observable<void>;
}
