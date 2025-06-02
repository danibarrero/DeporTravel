import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/shared/header/header.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, FooterComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'DeporTravel';

  showHeaderAndFooter = true;

  constructor(private router: Router) {
    this.router.events.subscribe(() => {
      const hiddenRoutes = ['/login', '/register'];
      this.showHeaderAndFooter = !hiddenRoutes.includes(this.router.url);
    });
  }
}
