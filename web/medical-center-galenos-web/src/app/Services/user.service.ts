import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class UserService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;

      constructor(private http: HttpClient) { }

      login(patientSpecialistPersonalEmail: string, userPassword: string): Observable<any> {

            let URL_USER = this.URL_API + 'user/login';

            let user : any = {
                  userPassword: userPassword,
                  patientId: {
                        patientEmail: patientSpecialistPersonalEmail
                  },
                  specialistId: {
                        specialistEmail: patientSpecialistPersonalEmail
                  },
                  personalId: {
                        personalEmail: patientSpecialistPersonalEmail
                  }
            };

            user = JSON.stringify(user);

            return this.http.post(URL_USER, user, {headers: this.headers});
      }

      findByUserIdAndStatusActive(userId: number): Observable<any>{

            let URL_USER = this.URL_API + 'user/findByUserIdAndStatusActive/' + userId;
    
            return this.http.get(URL_USER, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findAll(page: number): Observable<any>{

            let URL_USER = this.URL_API + 'user/findAll/' + page;
    
            return this.http.get(URL_USER, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      updateUserByUserIdAndUserStatus(userId: number, userStatus: string): Observable<any>{

            let URL_USER = this.URL_API + 'user/updateUserByUserIdAndUserStatus';
    
            let user: any = {
                userId: userId,
                userStatus: userStatus
            };
    
            user = JSON.stringify(user);
    
            return this.http.put(URL_USER, user, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }
}
