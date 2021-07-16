import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Professional } from '../model/professional';
import { Observable } from 'rxjs';

@Injectable()
export class ProfessionalService {

  private professionalsUrl: string;

  constructor(private http: HttpClient) {
    this.professionalsUrl = 'http://localhost:8080/professionals';
  }

  public findAll(): Observable<Professional[]> {
    return this.http.get<Professional[]>(this.professionalsUrl);
  }

  public save(user: Professional) {
    return this.http.post<Professional>(this.professionalsUrl, user);
  }
}