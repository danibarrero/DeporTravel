import { ActividadService } from './../../services/actividad.service';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

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
  selector: 'app-actividades',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './actividades.component.html',
  styleUrls: ['./actividades.component.css'],
})
export class ActividadesComponent implements OnInit {
  constructor(
    private router: Router,
    private actividadservice: ActividadService
  ) {}

  busqueda: string = '';

  data: Actividad[] = [];

  private listarActividades() {
    this.actividadservice.getAll().subscribe({
      next: (response: any) => {
        this.data = response;
        console.log(this.data);
      },
      error: (err) => {
        console.error('Error al obtener las colecciones:', err);
      },
    });
  }

  ngOnInit(): void {
    this.listarActividades();
  }

  get actividadesFiltradas() {
    return this.data.filter((actividad) =>
      actividad.nombre.toLowerCase().includes(this.busqueda.toLowerCase())
    );
  }

  goToActividad(id: number): void {
    this.router.navigate(['/inicio/actividad', id]);
  }
}
