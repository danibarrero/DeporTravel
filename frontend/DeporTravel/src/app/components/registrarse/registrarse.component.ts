import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registrarse',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './registrarse.component.html',
  styleUrls: ['./registrarse.component.css'],
})
export class RegistrarseComponent implements OnInit {
  registerForm!: FormGroup;
  errorMessage = '';
  mostrarContrasena = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required]],
      apellido: ['', [Validators.required]],
      correoElectronico: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    const formData = this.registerForm.value;

    this.authService
      .register(
        formData.nombre,
        formData.apellido,
        formData.contrasena,
        formData.correoElectronico,
        'USER'
      )
      .subscribe({
        next: () => {
          // Al registrar, redirige al login automáticamente
          this.router.navigate(['/login']);
        },
        error: (err) => {
          this.errorMessage =
            err.error?.message || 'Error al registrar. Intenta de nuevo.';
        },
      });
  }

  VerOcultarContrasena(): void {
    this.mostrarContrasena = !this.mostrarContrasena;
  }

  goBack(): void {
    window.history.back();
  }

  // Getters para validaciones
  get nombre() {
    return this.registerForm.get('nombre');
  }
  get apellido() {
    return this.registerForm.get('apellido');
  }
  get correoElectronico() {
    return this.registerForm.get('correoElectronico');
  }
  get contrasena() {
    return this.registerForm.get('contrasena');
  }
}
