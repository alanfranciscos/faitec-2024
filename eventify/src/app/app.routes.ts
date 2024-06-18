import { Routes } from '@angular/router';
import { SignInComponent } from './views/account/sign-in/sign-in.component';
import { SignUpComponent } from './views/account/sign-up/sign-up.component';
import { MainComponent } from './views/main/main.component';
import { authenticationGuard } from './services/security/guard/authentication.guard';
import { MyEventsComponent } from './views/events/my-events/my-events.component';
import { CreateEventsComponent } from './views/events/create-events/create-events.component';
import { EditEventsComponent } from './views/events/edit-events/edit-events.component';

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
        path: '',
        component: MainComponent,
        canActivate: [authenticationGuard],
        children: [
            {
                path: '',
                component: MainComponent
            },
            {
                path: 'my-events',
                component: MyEventsComponent,
            },
            {
                path: 'create-events',
                component: CreateEventsComponent,
            },
            {
                path: 'edit-events',
                component: EditEventsComponent,
            },

        ]
    }
];
