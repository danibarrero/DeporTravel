import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ActividadService } from '../../services/actividad.service';

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
  imports: [CommonModule, RouterModule],
  templateUrl: './actividad.component.html',
  styleUrls: ['./actividad.component.css'],
})
export class ActividadComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
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

  get actividadesFiltradas(): Actividad | undefined {
    return this.data.find((actividad) =>
      actividad.nombre.toLowerCase().includes(this.busqueda.toLowerCase())
    );
  }
}
