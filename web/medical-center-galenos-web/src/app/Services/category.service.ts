import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';


@Injectable()
export class CategoryService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;
  
      constructor(private http: HttpClient) { }
  

      findByBranchOfficeId(branchOfficeId: number): Observable<any>{
  
        let URL_CATEGORY = this.URL_API + 'category/findByBranchOfficeId/' + branchOfficeId;

        return this.http.get(URL_CATEGORY, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
    }

}