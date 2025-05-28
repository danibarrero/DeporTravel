import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ActividadService {

  httpClient = inject(HttpClient);

  url = 'http://localhost:8080/v1/api/actividades';

  getAll() {
    return this.httpClient.get<any>(`${this.url}/actividadesAll`);
  }
}
