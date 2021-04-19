import { HttpHeaders, HttpParams } from '@angular/common/http';

export type MethodOptions = {
  body?: any,
  params?: HttpParams | {
    [param: string]: string | string[];
  }
  reportProgress?: boolean,
  observe?: string
};

export type RequestOptions = {
  headers?: HttpHeaders;
  body?: any;
  params?: HttpParams | {
    [param: string]: string | string[];
  };
  responseType: 'json' | 'blob';
};

export const HEADERS = {
  GET: {Accept: 'application/json'},
  DELETE: {'Content-Type': 'application/json', Accept: 'application/json'},
  POST: {'Content-Type': 'application/json', Accept: 'application/json'},
  PUT: {'Content-Type': 'application/json', Accept: 'application/json'},
  POST_BLOB: {'Content-Type': 'multipart/form-data, boundary: {}', Accept: 'application/json'},
};

export const defaultRequestOptions: RequestOptions = {
  responseType: 'json'
};

export enum HttpMethod {
  GET = 'get',
  POST = 'post',
  PUT = 'put',
  DELETE = 'delete'
}
