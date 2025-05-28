import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-registrarse',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './registrarse.component.html',
  styleUrl: './registrarse.component.css',
})
export class RegistrarseComponent {
  nombre: string = '';
  apellido: string = '';
  email: string = '';
  contrasena: string = '';

  onSubmit() {
    console.log('Datos enviados:', {
      nombre: this.nombre,
      apellido: this.apellido,
      email: this.email,
      contrasena: this.contrasena
    });
    // Aquí puedes agregar lógica para enviar los datos al backend
  }
}
