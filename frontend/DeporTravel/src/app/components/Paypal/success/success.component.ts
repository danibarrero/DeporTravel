import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InscripcionService } from '../../../services/inscripcion.service';
import { StorageService } from '../../../services/storge.service';

const channel = new BroadcastChannel('succes-paypal-channel');

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css'],
  standalone: true,
})
export class SuccessComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly inscripcionService = inject(InscripcionService);
  private readonly storageService = inject(StorageService);

  ngOnInit() {
    const idActividad = Number(
      this.route.snapshot.queryParamMap.get('actividadId')
    );
    const usuario = this.storageService.getUser();

    if (!usuario || !idActividad) {
      console.warn('Falta usuario o idActividad');
      this.router.navigate(['/payment/cancel']);
      return;
    }

    this.inscripcionService
      .existeInscripcion(usuario.id, idActividad)
      .subscribe({
        next: (existe: boolean) => {
          if (existe) {
            // Ya inscrito
            this.router.navigate(['/payment/cancel']);
          } else {
            // Crear inscripción
            this.inscripcionService
              .crearInscripcion(usuario.id, idActividad)
              .subscribe({
                next: () => {
                  console.log('Inscripción creada correctamente');
                  channel.postMessage({ message: 'successPaypal' });
                  this.router.navigate(['/inicio']);
                },
                error: (err) => {
                  console.error('Error al crear inscripción:', err);
                  this.router.navigate(['/payment/cancel']);
                },
              });
          }
        },
        error: (err) => {
          console.error('Error al verificar inscripción:', err);
          this.router.navigate(['/payment/cancel']);
        },
      });
  }
}
