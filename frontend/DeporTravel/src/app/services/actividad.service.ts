import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ActividadService {
  private httpClient = inject(HttpClient);
  //private url = 'http://localhost:8080/v1/api/actividades';
  private url = 'https://deportravel.onrender.com/v1/api/actividades';

  getAll() {
    return this.httpClient.get(`${this.url}/actividadesAll`);
  }

  getCategorias() {
    return this.httpClient.get(`${this.url}/categorias`);
  }

  getPaises() {
    return this.httpClient.get(`${this.url}/paises`);
  }
}
