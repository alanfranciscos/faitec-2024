import { Routes } from '@angular/router';
import { LoginComponent } from './pages/authentication/login/login.component';
import { MainComponent } from './pages/main/main.component';
import { BasicInfoComponent } from './pages/events/create-event/basic-info/basic-info.component';
import { AddressInfoComponent } from './pages/events/create-event/address-info/address-info.component';
import { PaymentInfoComponent } from './pages/events/create-event/payment-info/payment-info.component';
import { FriendsComponent } from './pages/friends/friends.component';
import { MyEventsComponent } from './pages/events/my-events/my-events.component';
import { RegisterComponent } from './pages/authentication/register/register.component';
import { ForgetPasswordComponent } from './pages/authentication/forget-password/forget-password.component';
import { MyProfileComponent } from './pages/my-profile/my-profile.component';
import { authGuard } from './services/security/guard/auth.guard';
import { ManageEventComponent } from './pages/events/manage-event/manage-event.component';
import { ExpensesComponent } from './pages/events/manage-event/expenses/expenses.component';
import { MembersComponent } from './pages/events/manage-event/members/members.component';
import { GeneralViewComponent } from './pages/events/manage-event/general-view/general-view.component';
import { FriendComponent } from './pages/invites/friend/friend.component';
import { EventComponent } from './pages/invites/event/event.component';
import { ConfirmationComponent } from './pages/authentication/confirmation/confirmation.component';
import { _BasicInfoComponent } from './pages/events/edit-event/basic-info/basic-info.component';
import { _AddressInfoComponent } from './pages/events/edit-event/address-info/address-info.component';
import { _PaymentInfoComponent } from './pages/events/edit-event/payment-info/payment-info.component';

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
    path: 'account/:id/confirmation/:code',
    component: ConfirmationComponent,
  },
  {
    path: 'account/forget-password',
    component: ForgetPasswordComponent,
  },

  {
    path: 'my-profile',
    component: MyProfileComponent,
  },

  {
    path: '',
    component: MainComponent,
    canActivate: [authGuard], // Protegendo a rota principal
    children: [
      {
        path: '',
        component: MyEventsComponent,
        canActivate: [authGuard], // Protegendo a rota de eventos
      },
      {
        path: 'friends',
        component: FriendsComponent,
        canActivate: [authGuard],
      },
      {
        path: 'invite/friend',
        component: FriendComponent,
        canActivate: [authGuard],
      },
      {
        path: 'invite/event',
        component: EventComponent,
        canActivate: [authGuard],
      },
    ],
  },

  {
    path: 'create-event',

    children: [
      { path: 'basic-info', component: BasicInfoComponent },

      { path: 'address-info', component: AddressInfoComponent },

      { path: 'payment-info', component: PaymentInfoComponent },
    ],
  },

  {
    path: 'event/:id/edit-event',

    children: [
      { path: 'basic-info', component: _BasicInfoComponent },

      { path: 'address-info', component: _AddressInfoComponent },

      { path: 'payment-info', component: _PaymentInfoComponent },
    ],
  },

  {
    path: 'event/:id',
    component: ManageEventComponent,
    children: [
      { path: '', component: GeneralViewComponent },
      { path: 'expenses', component: ExpensesComponent },
      { path: 'members', component: MembersComponent },
    ],
  },
];
