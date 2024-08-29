// import { Routes } from '@angular/router';
// import { LoginComponent } from './pages/authentication/login/login.component';
// import { MainComponent } from './pages/main/main.component';
// import { BasicInfoComponent } from './pages/events/create-event/basic-info/basic-info.component';
// import { AddressInfoComponent } from './pages/events/create-event/address-info/address-info.component';
// import { PaymentInfoComponent } from './pages/events/create-event/payment-info/payment-info.component';
// import { NotificationComponent } from './pages/notification/notification.component';
// import { FriendsComponent } from './pages/friends/friends.component';
// import { ManageEventComponent } from './pages/events/manage-event/manage-event.component';
// import { GeneralViewComponent } from './pages/events/manage-event/general-view/general-view.component';
// import { ExpensesComponent } from './pages/events/manage-event/expenses/expenses.component';
// import { MembersComponent } from './pages/events/manage-event/members/members.component';

// export const routes: Routes = [
//   {
//     path: 'account/login',
//     component: LoginComponent,
//   },
//   {
//     path: '',
//     component: MainComponent,
//   },
//   {
//     path: 'create-event',
//     children: [
//       { path: 'basic-info', component: BasicInfoComponent },
//       { path: 'address-info', component: AddressInfoComponent },
//       { path: 'payment-info', component: PaymentInfoComponent },
//     ],
//   },
//   {
//     path: 'notification',
//     component: NotificationComponent,
//   },
//   {
//     path: 'friends',
//     component: FriendsComponent,
//   },
//   {
//     path: 'manage-event',
//     children: [
//       {
//         path: '',
//         component: ManageEventComponent,
//       },
//       {
//         path: 'expenses',
//         component: ExpensesComponent,
//       },
//       {
//         path: 'members',
//         component: MembersComponent,
//       },
//     ],
//   },

//   // {
//   //   path: 'account/create',
//   //   component: LoginComponent,
//   // },
//   // {
//   //   path: 'account/verify/{id}',
//   //   component: LoginComponent,
//   // },
// ];

import { Routes } from '@angular/router';

import { LoginComponent } from './pages/authentication/login/login.component';

import { MainComponent } from './pages/main/main.component';

import { BasicInfoComponent } from './pages/events/create-event/basic-info/basic-info.component';

import { AddressInfoComponent } from './pages/events/create-event/address-info/address-info.component';

import { PaymentInfoComponent } from './pages/events/create-event/payment-info/payment-info.component';

import { NotificationComponent } from './pages/notification/notification.component';

import { FriendsComponent } from './pages/friends/friends.component';

import { ManageEventComponent } from './pages/events/manage-event/manage-event.component';
import { ExpensesComponent } from './pages/events/manage-event/expenses/expenses.component';
import { MembersComponent } from './pages/events/manage-event/members/members.component';
import { GeneralViewComponent } from './pages/events/manage-event/general-view/general-view.component';
import { MyEventsComponent } from './pages/events/my-events/my-events.component';
import { RegisterComponent } from './pages/authentication/register/register.component';
import { ForgetPasswordComponent } from './pages/authentication/forget-password/forget-password.component';

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
        path: '',
        component: MyEventsComponent,
      },
      {
        path: 'notification',

        component: NotificationComponent,
      },

      {
        path: 'friends',

        component: FriendsComponent,
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
    path: 'manage-event',
    component: ManageEventComponent,
    children: [
      { path: 'general-view', component: GeneralViewComponent },
      { path: 'expenses', component: ExpensesComponent },
      { path: 'members', component: MembersComponent },
    ],
  },
];
