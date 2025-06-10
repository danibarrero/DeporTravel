import { Component, inject, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { StorageService } from '../../../services/storge.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  storageService = inject(StorageService);
  usuario: any = null;

  ngOnInit() {
    if (this.storageService.isLoggedIn()) {
      this.usuario = this.storageService.getUser();
    }
  }
}
