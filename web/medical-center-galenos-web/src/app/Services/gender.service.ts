import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';


@Injectable()
export class GenderService {

    private headers = new HttpHeaders().set('Content-Type','application/json');
    private URL_API : string = environment.URL_API;

    constructor(private http: HttpClient) { }

    findAll(): Observable<any>{

        let URL_GENDER = this.URL_API + 'gender/findAll';

        return this.http.get(URL_GENDER, {headers: this.headers});
    }

}
