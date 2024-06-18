import { Routes } from '@angular/router';
import { SignInComponent } from './views/account/sign-in/sign-in.component';
import { SignUpComponent } from './views/account/sign-up/sign-up.component';
import { MainComponent } from './views/main/main.component';
import { authenticationGuard } from './services/security/guard/authentication.guard';

export const routes: Routes = [
    {
        path: 'account/sign-in',
        component: SignInComponent
    },
    {
        path: 'account/sign-up',
        component: SignUpComponent
    },
    {
        path:'',
        component: MainComponent,
        canActivate: [authenticationGuard],
        children: [
            {
                path:'',
                component: MainComponent
            }
        ]
    }
];
