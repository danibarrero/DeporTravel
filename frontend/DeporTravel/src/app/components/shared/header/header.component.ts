import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { StorageService } from '../../../services/storge.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [RouterLink, CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn = false;
  username = '';

  constructor(
    private storageService: StorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    const user = this.storageService.getUser();
    this.username = user.nombre || user.correoElectronico;
  }

  cerrarSesion(): void {
    this.storageService.clean();
    this.router.navigate(['/login']).then(() => window.location.reload());
  }
}
