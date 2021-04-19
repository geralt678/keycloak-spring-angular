import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpConnectionService } from '../../connection/connection/http-connection.service';

const API_URL = '/api';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private httpClient: HttpConnectionService) {
  }

  getUserData(): Observable<any> {
    return this.httpClient.get(API_URL + '/user-data');
  }

  getAdminData(): Observable<any> {
    return this.httpClient.get(API_URL + '/admin-data');
  }

}
