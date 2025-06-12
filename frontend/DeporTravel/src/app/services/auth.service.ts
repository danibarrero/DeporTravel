import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { StorageService } from './storge.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  //private url = 'http://localhost:8080/v1/api/auth/';
  private url = 'https://deportravel.onrender.com/v1/api/auth';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private storageService: StorageService
  ) {}

  login(correoElectronico: string, contrasena: string): Observable<any> {
    return this.httpClient.post(
      this.url + 'login',
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
    apellido: string,
    contrasena: string,
    correoElectronico: string,
    rol: string
  ): Observable<any> {
    const registerRequest = {
      nombre,
      apellido,
      contrasena,
      correoElectronico,
      roles: [rol],
    };

    return this.httpClient.post(
      `${this.url}register`,
      registerRequest,
      this.httpOptions
    );
  }

  logout() {
    this.storageService.logout();
  }

  errorHandler(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => errorMessage);
  }

  getCurrentUser(): any {
    return this.storageService.getUser();
  }
}
