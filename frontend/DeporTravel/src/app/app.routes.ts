import { ActividadComponent } from './components/actividad/actividad.component';
import { ActividadesComponent } from './components/actividades/actividades.component';
import { IniciarSesionComponent } from './components/login/login.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { Routes } from '@angular/router';
import { SobreNosotrosComponent } from './components/sobre-nosotros/sobre-nosotros.component';
import { RegistrarseComponent } from './components/registrarse/registrarse.component';

export const routes: Routes = [
  {
    path: 'inicio',
    component: InicioComponent,
    title: 'Inicio'
  },
  {
    path: 'inicio/actividades',
    component: ActividadesComponent,
    title: 'Actividades'
  },
  {
    path: 'inicio/actividad/:id',
    component: ActividadComponent,
    title: 'Actividad'
  },
  {
    path: 'sobre-nosotros',
    component: SobreNosotrosComponent,
    title: 'Sobre Nosotros'
  },
  {
    path: 'login',
    component: IniciarSesionComponent,
    title: 'login'
  },
  {
    path: 'register',
    component: RegistrarseComponent,
    title: 'Registrarse'
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'inicio'
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'inicio'
  }
];
