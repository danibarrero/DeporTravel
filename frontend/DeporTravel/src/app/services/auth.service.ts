import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import { StorageService } from './storge.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiAuthURL =  'http://localhost:8080/v1/api/auth';


  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private httpClient: HttpClient, private storageService: StorageService) { }


  login(correoElectronico: string, contrasena: string): Observable<any> {
    return this.httpClient.post(
      this.apiAuthURL + 'login',
      {
        correoElectronico,
        contrasena,
      },
      this.httpOptions
    );
  }

  /* Al JWT Stateless no hace falta enviar petición al backend
    logout(): Observable<any> {
      return this.httpClient.post(this.apiURL + 'logout', { }, this.httpOptions);
    }
  */

  register(
    nombre: string,
    apellidos: string,
    contrasena: string,
    correoElectronico: string,
    rol: string
  ): Observable<any> {
    let registerRequest = {
    nombre: nombre,
    apellidos: apellidos,
    contrasena: contrasena,
    correoElectronico: correoElectronico,
      roles: [rol],
    };

    return this.httpClient.post(
      this.apiAuthURL + 'register',
      JSON.stringify(registerRequest),
      this.httpOptions
    );
  }

  logout() {
    this.storageService.clean();
  }

  errorHandler(error: any) {
    //this.storageService.clean();

    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => errorMessage);
  }
}

