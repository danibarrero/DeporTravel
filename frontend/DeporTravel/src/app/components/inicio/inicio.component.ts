import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { Actividad } from '../actividades/actividades.component';
import { ActividadService } from '../../services/actividad.service';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css'],
})
export class InicioComponent {
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
      },
      error: (err) => {
        console.error('Error al obtener las actividades:', err);
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

  // Navegar a la página de detalles de la actividad
  goToActividad(id: number): void {
    this.router.navigate(['/inicio/actividad', id]);
  }
}
