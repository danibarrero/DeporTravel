import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ActividadService } from './../../services/actividad.service';
import { StorageService } from '../../services/storge.service';

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
  busqueda: string = '';
  filtroPais: string = '';
  filtroCategoria: string = '';
  data: Actividad[] = [];
  paises: string[] = [];
  categorias: string[] = [];

  constructor(
    private router: Router,
    private actividadservice: ActividadService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    this.listarActividades();
  }

  private listarActividades() {
    this.actividadservice.getAll().subscribe({
      next: (response: any) => {
        this.data = response;
        this.cargarCategorias();
            this.cargarPaises();
      },
      error: (err) => {
        console.error('Error al obtener las actividades:', err);
      },
    });
  }

  private cargarCategorias() {
    const categoriasSet = new Set<string>();
    this.data.forEach((actividad) => categoriasSet.add(actividad.categoria));
    this.categorias = Array.from(categoriasSet);
  }

  private cargarPaises(): void {
    const paisesSet = new Set<string>();
    this.data.forEach((actividad) => paisesSet.add(actividad.pais));
    this.paises = Array.from(paisesSet);
  }

  get actividadesFiltradas() {
    return this.data.filter((actividad) => {
      const nombreMatch = actividad.nombre
        .toLowerCase()
        .includes(this.busqueda.toLowerCase());
      const paisMatch = !this.filtroPais || actividad.pais === this.filtroPais;
      const categoriaMatch =
        !this.filtroCategoria || actividad.categoria === this.filtroCategoria;
      return nombreMatch && paisMatch && categoriaMatch;
    });
  }

  goToActividad(id: number): void {
    this.router.navigate(['/inicio/actividad', id]);
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

  limpiarFiltros(): void {
    this.busqueda = '';
    this.filtroCategoria = '';
    this.filtroPais = '';
  }
}
