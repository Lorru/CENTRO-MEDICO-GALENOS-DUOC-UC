import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';


@Injectable()
export class SpecialtyService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;
  
      constructor(private http: HttpClient) { }
  

      findByBranchOfficeId(branchOfficeId: number): Observable<any>{
  
        let URL_SPECIALTY = this.URL_API + 'specialty/findByBranchOfficeId/' + branchOfficeId;

        return this.http.get(URL_SPECIALTY, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
    }

}
