import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthenticationService } from '../../authentication.service';

export const authGuard: 
  CanActivateFn = (route, state) => {

    const router = inject(Router);
    const authService = inject(AuthenticationService);

    const isAuthenticated = authService.isAuthenticated();

    if(isAuthenticated){
      return true;
    }

    if(router.url === 'account/register'){
      router.navigate(['account/login']);
      return false;
    }

    router.navigate(['account/login']);
    return false;
};
