import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "./Page";
import {Service} from "./Service";

export abstract class AbstractService<T> implements Service<T> {

  protected _httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/hal+json',
      'COMMANDS': 'BLOB'
    }),
  };

  protected constructor(protected httpClient: HttpClient) {
  }

  abstract getUri(v): string;

  list(page: Page): Observable<Page> {
    let metadata = new Page();
    metadata.index = page.index;
    metadata.size = page.size;
    metadata.filters = page.filters;
    metadata.projections = page.projections;
    metadata.sorts = page.sorts;
    return this.httpClient.get<Page>(this.getUri("/search/page/" + encodeURIComponent(JSON.stringify(metadata))), this._httpOptions);
  }

  create(obj): Observable<any> {
    return this.httpClient.post<T>(this.getUri(null), obj, this._httpOptions);
  }

  read(id): Observable<T> {
    return this.httpClient.get<T>(this.getUri('/' + id), this._httpOptions);
  }

  update(id, obj): Observable<any> {
    return this.httpClient.put<T>(this.getUri('/' + id), obj, this._httpOptions);
  }

  delete(id): Observable<any> {
    return this.httpClient.delete(this.getUri('/' + id), this._httpOptions);
  }

  deleteAll(...identities: any[]): Observable<any> {
    return this.httpClient.delete(this.getUri('/batch/delete/' + identities.join(",")), this._httpOptions);
  }
}
