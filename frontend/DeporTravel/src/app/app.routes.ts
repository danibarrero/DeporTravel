import { ActividadComponent } from './components/actividad/actividad.component';
import { ActividadesComponent } from './components/actividades/actividades.component';
import { IniciarSesionComponent } from './components/login/login.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { Routes } from '@angular/router';
import { SobreNosotrosComponent } from './components/sobre-nosotros/sobre-nosotros.component';
import { RegistrarseComponent } from './components/registrarse/registrarse.component';
import { PaypalComponent } from './components/Paypal/paypal.component';
import { CancelComponent } from './components/Paypal/cancel/cancel.component';
import { SuccessComponent } from './components/Paypal/success/success.component';
import { PerfilComponent } from './components/perfil/perfil.component';
import { CambiarContrasenaComponent } from './components/cambiar-contrasena/cambiar-contrasena.component';
import { MisActividadesComponent } from './components/mis-actividades/mis-actividades.component';
import { AuthGuard } from './services/auth.guard';
import { AdministrarComponent } from './components/administrar/administrar.component';

export const routes: Routes = [
  {
    path: 'inicio',
    component: InicioComponent,
    title: 'Inicio',
  },
  {
    path: 'inicio/actividades',
    component: ActividadesComponent,
    title: 'Actividades',
  },
  {
    path: 'inicio/actividad/:id',
    component: ActividadComponent,
    title: 'Actividad',
  },
  {
    path: 'sobre-nosotros',
    component: SobreNosotrosComponent,
    title: 'Sobre Nosotros',
  },
  {
    path: 'login',
    component: IniciarSesionComponent,
    title: 'Login',
  },
  {
    path: 'register',
    component: RegistrarseComponent,
    title: 'Registrarse',
  },
  {
    path: 'paypal/:idActividad',
    component: PaypalComponent,
    title: 'Paypal',
  },
  {
    path: 'payment/success',
    component: SuccessComponent,
    title: 'Paypal',
  },
  {
    path: 'payment/cancel',
    component: CancelComponent,
    title: 'Paypal',
  },
  {
    path: 'perfil',
    component: PerfilComponent,
    title: 'Perfil',
    canActivate: [AuthGuard],
  },
  {
    path: 'cambiar-contrasena',
    component: CambiarContrasenaComponent,
    title: 'Cambiar Contraseña',
    canActivate: [AuthGuard],
  },
  {
    path: 'mis-actividades',
    component: MisActividadesComponent,
    title: 'Mis Actividades',
    canActivate: [AuthGuard],
  },
  {
    path: 'administrar',
    component: AdministrarComponent,
    title: 'Administrar',
  },

  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'inicio',
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'inicio',
  },
];
