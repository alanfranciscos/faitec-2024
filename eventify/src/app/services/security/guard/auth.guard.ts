import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthenticationService } from '../../authentication.service';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthenticationService);

  const isAuthenticated = authService.isAuthenticated();

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
