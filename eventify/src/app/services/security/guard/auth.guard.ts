import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthenticationService } from '../authentication/authentication.service';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthenticationService);

  const isAuthenticated = authService.isAuthenticated();

  const blockedUrlWhenLogged = [
    'account/register',
    'account/login',
    'forget-password',
  ];

  const blockedRouteWhenLogged = blockedUrlWhenLogged.includes(state.url);

  if (isAuthenticated && blockedRouteWhenLogged) {
    router.navigate(['']);
    return true;
  }

  if (isAuthenticated) {
    return true;
  }

  // Permite acesso à página de registro se não estiver autenticado
  if (state.url.includes('account/register')) {
    return true;
  }

  // Redireciona para a página de login se não estiver autenticado
  router.navigate(['account/login']);
  return false;
};
