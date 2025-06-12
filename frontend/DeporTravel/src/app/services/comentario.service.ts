import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Comentario {
  id?: number;
  comentario: string;
  fechaComentario: string;
  usuario?: any;
  actividad?: any;
}

@Injectable({
  providedIn: 'root',
})
export class ComentarioService {
  //private url = 'http://localhost:8080/v1/api/comentarios';
  private url = 'https://deportravel.onrender.com/v1/api/comentarios';

  constructor(private http: HttpClient) {}

  getAllComentarios() {
    return this.http.get<Comentario[]>(`${this.url}/comentariosAll`);
  }

  getComentariosPorActividad(idActividad: number) {
    return this.http.get<Comentario[]>(`${this.url}/actividad/${idActividad}`);
  }

  createComentario(
    idUsuario: number,
    idActividad: number,
    comentario: Comentario
  ) {
    return this.http.post<Comentario>(
      `${this.url}/${idUsuario}/${idActividad}`,
      comentario
    );
  }
}
