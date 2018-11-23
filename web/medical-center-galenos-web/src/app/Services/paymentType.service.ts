import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class PaymentTypeService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;

      constructor(private http: HttpClient) { }

      findAll(): Observable<any>{

            let URL_PAYMENT_TYPE = this.URL_API + 'paymentType/findAll';

            return this.http.get(URL_PAYMENT_TYPE, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

}
