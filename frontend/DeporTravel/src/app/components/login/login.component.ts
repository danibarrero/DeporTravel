import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StorageService } from '../../services/storge.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-iniciar-sesion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class IniciarSesionComponent implements OnInit {
  loginForm!: FormGroup;

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private storageService: StorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      correoElectronico: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9._%+-]+@test\.com$')]],
      contrasena: ['', [Validators.required]],
    });

    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.roles = this.storageService.getUser().roles;
      this.router.navigateByUrl('index').then(() => {
        console.log('Ya logueado, cargando index.');
      });
    }
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const { correoElectronico, contrasena } = this.loginForm.value;

    this.authService.login(correoElectronico, contrasena).subscribe({
      next: (data) => {
        this.storageService.clean();
        this.storageService.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        console.log('isLoggedIn = ' + this.isLoggedIn);
        this.roles = this.storageService.getUser().roles;
        this.reloadPage();
      },
      error: (err) => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      },
    });
  }

  reloadPage(): void {
    window.location.reload();
  }

  get contrasena() {
    return this.loginForm.get('contrasena');
  }

  get correoElectronico() {
    return this.loginForm.get('correoElectronico');
  }
}
