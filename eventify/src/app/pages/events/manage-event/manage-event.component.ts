import { Component } from '@angular/core';
import { EventLayoutComponent } from '../../../components/sidebar/event-sidebar/event-sidebar.component';
import { FooterComponent } from '../../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { GeneralViewComponent } from './general-view/general-view.component';
import { ExpensesComponent } from './expenses/expenses.component';
import { MembersComponent } from './members/members.component';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-manage-event',
  standalone: true,
  imports: [
    EventLayoutComponent,
    FooterComponent,
    CommonModule,
    GeneralViewComponent,
    ExpensesComponent,
    MembersComponent,
    RouterOutlet,
    RouterLink,
  ],
  templateUrl: './manage-event.component.html',
  styleUrl: './manage-event.component.scss',
})
export class ManageEventComponent {}
