import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class ExceptionLogService {

  private headers = new HttpHeaders().set('Content-Type','application/json');
  private URL_API : string = environment.URL_API;

  constructor(private http: HttpClient) { }

  findAll(page: number): Observable<any>{

      let URL_EXCEPTION_LOG = this.URL_API + 'exceptionLog/findAll/' + page;

      return this.http.get(URL_EXCEPTION_LOG, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
  }

}
