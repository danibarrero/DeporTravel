import { StorageService } from './storge.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private url = 'http://localhost:8080/v1/api/usuarios';

  constructor(
    private http: HttpClient,
    private storageService: StorageService
  ) {}

  getUsuarios() {
    return this.http.get<any[]>(`${this.url}/usuariosAll`);
  }

  getUsuarioPorId(id: number) {
    return this.http.get<any>(`${this.url}/${id}`);
  }

  cambiarContrasena(id: number, nuevaContrasena: string) {
    const usuario = this.storageService.getUser();
    const token = usuario?.token;

    console.log(token);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.put<void>(
      `${this.url}/${id}/contrasena`,
      { nuevaContrasena },
      { headers }
    );
  }

  actualizarUsuario(id: number, datos: any) {
    return this.http.put<any>(`${this.url}/${id}`, datos);
  }

  eliminarUsuario(id: number) {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
