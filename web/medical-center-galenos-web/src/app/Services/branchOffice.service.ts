import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class BranchOfficeService {

    private headers = new HttpHeaders().set('Content-Type','application/json');
    private URL_API : string = environment.URL_API;

    constructor(private http: HttpClient) { }

    findAll(): Observable<any>{

        let URL_BRANCH_OFFICE = this.URL_API + 'branchOffice/findAll';

        return this.http.get(URL_BRANCH_OFFICE, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
    }

}
