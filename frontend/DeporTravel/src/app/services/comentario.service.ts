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
  providedIn: 'root'
})
export class ComentarioService {
  baseUrl = 'http://localhost:8080/v1/api/comentarios';

  constructor(private http: HttpClient) {}

  getAllComentarios(): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(`${this.baseUrl}/comentariosAll`);
  }

  createComentario(idUsuario: number, idActividad: number, comentario: Comentario): Observable<Comentario> {
    return this.http.post<Comentario>(`${this.baseUrl}/${idUsuario}/${idActividad}`, comentario);
  }
}
