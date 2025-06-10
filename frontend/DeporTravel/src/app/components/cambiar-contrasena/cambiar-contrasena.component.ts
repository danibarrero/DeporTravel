import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service'; // Ajusta la ruta según corresponda
import { StorageService } from '../../services/storge.service';

@Component({
  selector: 'app-cambiar-contrasena',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cambiar-contrasena.component.html',
  styleUrls: ['./cambiar-contrasena.component.css'],
})
export class CambiarContrasenaComponent implements OnInit {
  formRecuperar: FormGroup;
  usuarioId: number | undefined;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private usuarioService: UsuarioService,
    private storageService: StorageService
  ) {
    this.formRecuperar = this.fb.group(
      {
        contrasena: ['', [Validators.required, Validators.minLength(6)]],
        repetirContrasena: ['', [Validators.required]],
      },
      { validators: this.passwordsIguales }
    );
  }

  ngOnInit(): void {
    // Obtén el usuario actual para tener el id
    const usuario = this.storageService.getUser();
    this.usuarioId = usuario?.id;
  }

  passwordsIguales(
    control: AbstractControl
  ): { [key: string]: boolean } | null {
    const pass = control.get('contrasena')?.value;
    const repetir = control.get('repetirContrasena')?.value;
    return pass && repetir && pass !== repetir ? { noCoincide: true } : null;
  }

  onSubmit(): void {
    if (this.formRecuperar.invalid || !this.usuarioId) {
      return;
    }

    const nuevaContrasena = this.formRecuperar.get('contrasena')?.value;

    this.usuarioService
      .cambiarContrasena(this.usuarioId, nuevaContrasena)
      .subscribe({
        next: () => {
          this.storageService.logout();
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.error(err);
          alert('Error al cambiar la contraseña. Inténtalo de nuevo.');
        },
      });
  }

  goBack(): void {
    window.history.back();
  }
}
