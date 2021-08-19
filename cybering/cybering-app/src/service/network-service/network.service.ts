import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Network } from 'src/app/interfaces/network';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NetworkService extends BaseService {
  constructor(private http: HttpClient) {
    super();
  }




}
