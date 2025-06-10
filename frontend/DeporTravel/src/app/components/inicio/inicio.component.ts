import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { Actividad } from '../actividades/actividades.component';
import { ActividadService } from '../../services/actividad.service';
import { StorageService } from '../../services/storge.service'; 

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css'],
})
export class InicioComponent implements OnInit {
  busqueda: string = '';
  data: Actividad[] = [];

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
      },
      error: (err) => {
        console.error('Error al obtener las actividades:', err);
      },
    });
  }

  get actividadesFiltradas() {
    return this.data.filter((actividad) =>
      actividad.nombre.toLowerCase().includes(this.busqueda.toLowerCase())
    );
  }

  goToActividad(id: number): void {
    this.router.navigate(['/inicio/actividad', id]);
  }

  comprarActividad(actividad: Actividad): void {
    if (this.storageService.isLoggedIn()) {
      // Usuario logueado, va a PayPal
      this.router.navigate(['/paypal', actividad.id], {
        queryParams: {
          amount: actividad.precio,
        },
      });
    } else {
      // No está logueado, va a login
      this.router.navigate(['/login']);
    }
  }
}
