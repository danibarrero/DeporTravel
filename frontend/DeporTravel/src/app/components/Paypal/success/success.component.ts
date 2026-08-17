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

  yaRegistrado = false;
  error = false;

  ngOnInit() {
    const idActividad = Number(
      this.route.snapshot.queryParamMap.get('actividadId')
    );
    const usuario = this.storageService.getUser();

    if (!usuario || !idActividad) {
      console.warn('Falta usuario o idActividad');
      this.error = true;
      return;
    }

    this.inscripcionService
      .existeInscripcion(usuario.id, idActividad)
      .subscribe({
        next: (existe: boolean) => {
          if (existe) {
            this.yaRegistrado = true;
            channel.postMessage({ message: 'successPaypal' });
          } else {
            this.inscripcionService
              .crearInscripcion(usuario.id, idActividad)
              .subscribe({
                next: () => {
                  console.log('Inscripción creada correctamente');
                  channel.postMessage({ message: 'successPaypal' });
                },
                error: (err) => {
                  console.error('Error al crear inscripción:', err);
                  this.error = true;
                },
              });
          }
        },
        error: (err) => {
          console.error('Error al verificar inscripción:', err);
          this.error = true;
        },
      });
  }

  goInicio(): void {
    this.router.navigate(['/inicio']);
  }
}
