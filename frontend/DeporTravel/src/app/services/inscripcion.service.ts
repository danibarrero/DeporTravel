import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storge.service';
import { Actividad } from '../components/actividad/actividad.component';

@Injectable({
  providedIn: 'root',
})
export class InscripcionService {
  //private url = 'http://localhost:8080/v1/api/inscripciones';
  private url = 'https://deportravel.onrender.com/v1/api/inscripciones';

  constructor(
    private http: HttpClient,
    private storageService: StorageService
  ) {}

  crearInscripcion(idUsuario: number, idActividad: number) {
    const usuario = this.storageService.getUser();
    const token = usuario?.token;

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.post(
      `${this.url}/${idUsuario}/${idActividad}`,
      {},
      { headers }
    );
  }

  existeInscripcion(idUsuario: number, idActividad: number) {
    return this.http.get<boolean>(
      `${this.url}/exists/${idUsuario}/${idActividad}`
    );
  }

  getActividadesPorUsuario(idUsuario: number) {
    const usuario = this.storageService.getUser();
    const token = usuario?.token;

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get<Actividad[]>(
      `${this.url}/actividadesPorUsuario/${idUsuario}`,
      { headers }
    );
  }
}
