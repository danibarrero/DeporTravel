import { Component } from '@angular/core';
import { Router, RouterOutlet, NavigationEnd } from '@angular/router';
import { HeaderComponent } from './components/shared/header/header.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, FooterComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'DeporTravel';
  showHeaderAndFooter = true;
  showOnlyHeader: boolean | undefined;

  constructor(private router: Router) {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        const hiddenRoutes = [
          '/login',
          '/register',
          '/paypal',
          '/payment/success',
          '/payment/cancel',
          '/perfil',
          '/cambiar-contrasena',
        ];

        if (
          this.router.url.startsWith('/mis-actividades') ||
          this.router.url.startsWith('/administrar')
        ) {
          this.showHeaderAndFooter = false;
          this.showOnlyHeader = true;
        } else {
          this.showHeaderAndFooter = !hiddenRoutes.some((route) =>
            this.router.url.startsWith(route)
          );
          this.showOnlyHeader = false;
        }
      });
  }
}
