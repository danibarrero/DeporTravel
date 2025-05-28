import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ActividadService } from '../../services/actividad.service';
import { Comentario, ComentarioService } from '../../services/comentario.service';
import { FormsModule } from '@angular/forms';

export interface Actividad {
  id: number;
  nombre: string;
  categoria: string;
  pais: string;
  fecha: Date;
  precio: number;
  descripcion: string;
  imagen: string;
}

@Component({
  selector: 'app-actividad',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './actividad.component.html',
  styleUrls: ['./actividad.component.css'],
})
export class ActividadComponent implements OnInit {
  busqueda: string = '';
  data: Actividad[] = [];
  comentarios: Comentario[] = [];
  nuevoComentario: string = '';

  constructor(
    private actividadservice: ActividadService,
    private comentarioService: ComentarioService
  ) {}

  ngOnInit(): void {
    this.listarActividades();
  }

  listarActividades() {
    this.actividadservice.getAll().subscribe({
      next: (actividades) => {
        this.data = actividades;
        this.cargarComentarios();
      },
      error: (err) => console.error('Error actividades:', err)
    });
  }

  get actividadesFiltradas(): Actividad | undefined {
    return this.data.find((actividad) =>
      actividad.nombre.toLowerCase().includes(this.busqueda.toLowerCase())
    );
  }

  cargarComentarios() {
    this.comentarioService.getAllComentarios().subscribe({
      next: (comentarios: any[]) => {
        const actividad = this.actividadesFiltradas;
        if (actividad) {
          this.comentarios = comentarios.filter(
            c => c.actividad?.id === actividad.id
          );
        }
      },
      error: (err: any) => console.error('Error comentarios:', err)
    });
  }

  enviarComentario() {
    const actividad = this.actividadesFiltradas;
    if (!actividad) {
      alert('No hay actividad seleccionada.');
      return;
    }
    if (!this.nuevoComentario.trim()) {
      alert('El comentario no puede estar vacío.');
      return;
    }

    const idUsuario = 1; // Cambia aquí según usuario logueado

    const comentarioBody = {
      comentario: this.nuevoComentario,
      fechaComentario: new Date().toISOString().split('T')[0]
    };

    this.comentarioService.createComentario(idUsuario, actividad.id, comentarioBody).subscribe({
      next: () => {
        this.nuevoComentario = '';
        this.cargarComentarios();
      },
      error: (err: any) => {
        console.error('Error creando comentario:', err);
        alert('Error al enviar comentario');
      }
    });
  }
}
