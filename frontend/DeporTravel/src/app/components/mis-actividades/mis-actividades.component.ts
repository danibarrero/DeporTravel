import { Component, OnInit } from '@angular/core';
import { Actividad } from '../actividad/actividad.component';
import { StorageService } from '../../services/storge.service';
import { InscripcionService } from '../../services/inscripcion.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mis-actividades',
  imports: [CommonModule],
  templateUrl: './mis-actividades.component.html',
  styleUrls: ['./mis-actividades.component.css']
})
export class MisActividadesComponent implements OnInit {
  actividades: Actividad[] = [];
  usuarioId!: number;

  constructor(
    private inscripcionService: InscripcionService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    const usuario = this.storageService.getUser();
    if (usuario) {
      this.usuarioId = usuario.id;
      this.cargarActividades();
    } else {
      console.warn('No hay usuario logueado.');
    }
  }

  cargarActividades(): void {
    this.inscripcionService.getActividadesPorUsuario(this.usuarioId).subscribe({
      next: (data: Actividad[]) => {
        console.log('Actividades recibidas:', data);  // Para debug
        this.actividades = data;
      },
      error: (error) => {
        console.error('Error al cargar actividades', error);
      }
    });
  }
}
