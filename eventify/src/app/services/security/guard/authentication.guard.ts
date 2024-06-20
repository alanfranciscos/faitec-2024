import { CanActivateFn, Router } from '@angular/router';
import { AuthenticationService } from '../../authentication.service';

export const authenticationGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authenticationService = inject(AuthenticationService);
  const isAuthenticated = this.authenticationService.isAuthenticated();
};
