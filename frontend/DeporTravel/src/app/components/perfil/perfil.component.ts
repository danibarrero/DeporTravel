import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { StorageService } from '../../services/storge.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css'],
})
export class PerfilComponent implements OnInit {
  usuario: any;
  perfilForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private storageService: StorageService,
    private router: Router
  ) {
    this.perfilForm = this.fb.group({
      nombre: [{ value: '', disabled: true }],
      apellido: [{ value: '', disabled: true }],
      correo: [{ value: '', disabled: true }],
    });
  }

  ngOnInit(): void {
    this.usuario = this.storageService.getUser();
    if (this.usuario) {
      this.perfilForm.patchValue({
        nombre: this.usuario.nombre,
        apellido: this.usuario.apellido,
        correo: this.usuario.correoElectronico,
      });
    }
  }

  irCambiarContrasena(event: Event): void {
    event.preventDefault();
    this.router.navigate(['/cambiar-contrasena']);
  }

  goBack(): void {
    window.history.back();
  }
}
