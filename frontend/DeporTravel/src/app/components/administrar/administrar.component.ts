import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { UsuarioService } from "../../services/usuario.service"

interface Usuario {
  id: number
  nombre: string
  apellido: string
  correoElectronico: string
}

@Component({
  selector: "app-administrar",
  imports: [CommonModule, FormsModule],
  templateUrl: "./administrar.component.html",
  styleUrls: ["./administrar.component.css"],
})
export class AdministrarComponent implements OnInit {
  usuarios: Usuario[] = []
  editandoUsuarioId: number | null = null
  usuarioEditado: Partial<Usuario> = {}
  cargando = false
  error: string | null = null

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarUsuarios()
  }

  cargarUsuarios() {
    this.cargando = true

    this.usuarioService.getUsuarios().subscribe({
      next: (data: Usuario[]) => {
        this.usuarios = data
        this.cargando = false
      }
    })
  }

  editarUsuario(usuario: Usuario) {
    this.editandoUsuarioId = usuario.id
    this.usuarioEditado = { ...usuario }
  }

  guardarUsuario() {
    if (!this.usuarioEditado.id) return

    this.usuarioService.actualizarUsuario(this.usuarioEditado.id, this.usuarioEditado).subscribe({
      next: () => {
        this.editandoUsuarioId = null
        this.cargarUsuarios()
      }
    })
  }

  cancelarEdicion() {
    this.editandoUsuarioId = null
    this.usuarioEditado = {}
  }

  eliminarUsuario(id: number) {
    if (confirm("¿Seguro que quieres eliminar este usuario?")) {
      this.usuarioService.eliminarUsuario(id).subscribe({
        next: () => {
          this.cargarUsuarios()
        }
      })
    }
  }
}
