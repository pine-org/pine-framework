import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "./Page";

export interface Service<T> {
  getUri(v): string;

  list(page: Page): Observable<Page>;

  create(obj): Observable<T>;

  read(id): Observable<T>;

  update(id, obj): Observable<T>;

  delete(id): any;

  deleteAll(...identities: any[]): any;
}

export abstract class AbstractService<T> implements Service<T> {

  protected _httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/hal+json',
      'Authorization': 'Basic ' + btoa('admin:password')
    }),
  };

  constructor(protected httpClient: HttpClient) {
  }

  abstract getUri(v): string;

  list(page: Page): Observable<Page> {
    let metadata = new Page();
    metadata.index = page.index;
    metadata.size = page.size;
    metadata.filters = page.filters;
    return this.httpClient.get<Page>(this.getUri(encodeURIComponent(JSON.stringify(metadata))), this._httpOptions);
  }

  create(obj): Observable<T> {
    return this.httpClient.post<T>(this.getUri(null), obj, this._httpOptions);
  }

  read(id): Observable<T> {
    return this.httpClient.get<T>(this.getUri('/' + id), this._httpOptions);
  }

  update(id, obj): Observable<T> {
    return this.httpClient.put<T>(this.getUri('/' + id), obj, this._httpOptions);
  }

  delete(id) {
    this.httpClient.delete(this.getUri('/' + id), this._httpOptions);
  }

  deleteAll(...identities: any[]): any {
    this.httpClient.delete(this.getUri('/delete-all/' + identities.join(",")), this._httpOptions);
  }
}
