import { Component, OnInit } from '@angular/core';
import { EventLayoutComponent } from '../../../components/sidebar/event-sidebar/event-sidebar.component';
import { FooterComponent } from '../../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { ExpensesComponent } from './expenses/expenses.component';
import { MembersComponent } from './members/members.component';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';
import { GeneralViewComponent } from './general-view/general-view.component';
import { EventService } from '../../../services/event/event.service';
import { HeaderComponent } from '../../../components/header/header.component';

@Component({
  selector: 'app-event',
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
    HeaderComponent,
  ],
  templateUrl: './manage-event.component.html',
  styleUrl: './manage-event.component.scss',
})
export class ManageEventComponent {
  isCollapsed = false; // Inicializa como false (sidebar expandida)

  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed; // Alterna o estado da sidebar
  }
}
