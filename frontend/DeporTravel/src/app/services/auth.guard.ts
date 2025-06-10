import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const user = this.authService.getCurrentUser();

    if (user && user.roles && user.roles.includes('USER')) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
