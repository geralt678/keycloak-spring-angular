import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { defaultRequestOptions, HEADERS, HttpMethod, MethodOptions, RequestOptions } from './http-connection.type';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpConnectionService {

  constructor(private httpClient: HttpClient) {
  }

  private static buildRequestOptions(methodHeaders: { [header: string]: string | string[] }, options: MethodOptions): RequestOptions {
    return {
      ...defaultRequestOptions,
      headers: new HttpHeaders(methodHeaders),
      ...options
    };
  }

  get(path: string, options: MethodOptions = {}): Observable<any> {
    const requestOptions: RequestOptions = HttpConnectionService.buildRequestOptions(HEADERS.GET, options);
    return this.request(HttpMethod.GET, path, requestOptions);
  }

  post(path: string, options: MethodOptions = {}): Observable<any> {
    const requestOptions: RequestOptions = HttpConnectionService.buildRequestOptions(HEADERS.POST, options);
    return this.request(HttpMethod.POST, path, requestOptions);
  }

  put(path: string, options: MethodOptions = {}): Observable<any> {
    const requestOptions: RequestOptions = HttpConnectionService.buildRequestOptions(HEADERS.PUT, options);
    return this.request(HttpMethod.PUT, path, requestOptions);
  }

  delete(path: string, options: MethodOptions = {}): Observable<any> {
    const requestOptions: RequestOptions = HttpConnectionService.buildRequestOptions(HEADERS.DELETE, options);
    return this.request(HttpMethod.DELETE, path, requestOptions);
  }

  postBlob(path: string, options: MethodOptions = {}): Observable<any> {
    const requestOptions: RequestOptions = HttpConnectionService.buildRequestOptions({}, Object.assign(options, {
      ...options,
      reportProgress: true,
      observe: 'events',
    }) as MethodOptions);
    return this.request(HttpMethod.POST, path, requestOptions);
  }

  private request(method: HttpMethod, path: string, options?: RequestOptions): Observable<any> {
    const url = environment.apiUrl + path;
    return this.httpClient.request(method, url, options);
  }
}
