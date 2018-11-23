import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class ScheduleService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;
  
      constructor(private http: HttpClient) { }

      findAllBySpecialistIdAndByBillMedicalTime(specialistId: number, billMedicalTime: string): Observable<any>{
  
            let URL_SCHEDULE = this.URL_API + 'schedule/findAllBySpecialistIdAndByBillMedicalTime/' + specialistId + '/' + billMedicalTime;
    
            return this.http.get(URL_SCHEDULE, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

}
