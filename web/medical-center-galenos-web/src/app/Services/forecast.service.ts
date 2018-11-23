import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class ForecastService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;

      constructor(private http: HttpClient) { }

      findAll(): Observable<any>{

            let URL_FORECAST = this.URL_API + 'forecast/findAll';

            return this.http.get(URL_FORECAST, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

}
