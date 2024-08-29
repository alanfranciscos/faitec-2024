import { Routes } from '@angular/router';
import { MainComponent } from './views/main/main.component';
import { authenticationGuard } from './services/security/guard/authentication.guard';
import { MyEventsComponent } from './views/events/my-events/my-events.component';
import { CreateEventsComponent } from './views/events/create-events/create-events.component';
import { EditEventsComponent } from './views/events/edit-events/edit-events.component';
import { MyExpensesComponent } from './views/my-expenses/my-expenses.component';
import { NotificationCenterComponent } from './views/notification-center/notification-center.component';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { NotFoundComponent } from './views/not-found/not-found.component';
import { LoginComponent } from './views/authentication/login/login.component';
import { RegisterComponent } from './views/authentication/register/register.component';
import { ForgetPasswordComponent } from './views/authentication/forget-password/forget-password.component';

export const routes: Routes = [
  {
    path: 'account/login',

    component: LoginComponent,
  },
  {
    path: 'account/register',

    component: RegisterComponent,
  },
  {
    path: 'account/forget-password',

    component: ForgetPasswordComponent,
  },
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: 'events',
        children: [
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
        ],
      },
      {
        path: 'expenses',
        children: [
          {
            path: '',
            component: MyExpensesComponent,
          },
        ],
      },
      {
        path: 'notification',
        children: [
          {
            path: '',
            component: NotificationCenterComponent,
          },
        ],
      },
      {
        path: 'dashboard',
        children: [
          {
            path: '',
            component: DashboardComponent,
          },
        ],
      },

      {
        path: '**',
        component: NotFoundComponent,
      },
    ],
  },
];
