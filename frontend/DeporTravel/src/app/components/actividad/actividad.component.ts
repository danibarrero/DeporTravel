import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ActividadService } from '../../services/actividad.service';
import {
  Comentario,
  ComentarioService,
} from '../../services/comentario.service';
import { FormsModule } from '@angular/forms';
import { StorageService } from './../../services/storge.service';

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
  actividad: Actividad | undefined;
  comentarios: Comentario[] = [];
  nuevoComentario: string = '';
  usuario: any = null;
  data: any;

  constructor(
    private route: ActivatedRoute,
    private actividadService: ActividadService,
    private comentarioService: ComentarioService,
    private storageService: StorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
  this.usuario = this.storageService.getUser();

  // Primero cargamos todas las actividades
  this.actividadService.getAll().subscribe({
    next: (actividades) => {
      this.data = actividades;

      // Después obtenemos el id de la ruta y cargamos la actividad
      this.route.params.subscribe((params) => {
        const id = +params['id'];
        this.cargarActividad(id);
      });
    },
    error: (err) => console.error('Error cargando actividades:', err),
  });
}


  cargarActividad(id: number) {
    // Buscar en el array de actividades cargadas
    const actividadEncontrada = this.data.find(
      (actividad: { id: number }) => actividad.id === id
    );

    if (!actividadEncontrada) {
      console.error('Actividad no encontrada');
      return;
    }

    this.actividad = actividadEncontrada;
    this.cargarComentarios();
  }

  cargarComentarios() {
    if (!this.actividad) return;
    this.comentarioService
      .getComentariosPorActividad(this.actividad.id)
      .subscribe({
        next: (comentarios) => (this.comentarios = comentarios),
        error: (err) => console.error('Error comentarios:', err),
      });
  }

  enviarComentario() {
    if (!this.actividad) return;

    const usuario = this.storageService.getUser();

    if (!usuario?.id) {
      alert('Debe estar logueado para comentar.');
      return;
    }

    if (!this.nuevoComentario.trim()) {
      alert('El comentario no puede estar vacío.');
      return;
    }

    const comentarioBody = {
      comentario: this.nuevoComentario,
      fechaComentario: new Date().toISOString().split('T')[0],
    };

    this.comentarioService
      .createComentario(usuario.id, this.actividad.id, comentarioBody)
      .subscribe({
        next: () => {
          this.nuevoComentario = '';
          this.cargarComentarios();
        },
        error: (err) => {
          console.error('Error al enviar comentario:', err);
          alert('Error al enviar comentario');
        },
      });
  }

  comprarActividad(actividad: Actividad, event: Event): void {
    event.stopPropagation();
    if (this.storageService.isLoggedIn()) {
      this.router.navigate(['/paypal', actividad.id], {
        queryParams: { amount: actividad.precio },
      });
    } else {
      this.router.navigate(['/login']);
    }
  }
}
