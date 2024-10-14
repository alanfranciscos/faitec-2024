import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MainSidebarComponent } from '../../components/sidebar/main-sidebar/main-sidebar.component';
import { MyEventsComponent } from '../events/my-events/my-events.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterOutlet } from '@angular/router';
import { FullBannerComponent } from '../../components/ad/full-banner/full-banner.component';
import { DropBannerComponent } from '../../components/ad/drop-banner/drop-banner.component';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    MainSidebarComponent,
    MyEventsComponent,
    FooterComponent,
    RouterOutlet,
    CommonModule,
    FullBannerComponent,
    DropBannerComponent,
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent {
  isCollapsed = false; // Inicializa como false (sidebar expandida)

  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed; // Alterna o estado da sidebar
  }
  isAdFullBannerDialogOpen = true;

  toggleAdFullBanner() {
    this.isAdFullBannerDialogOpen = !this.isAdFullBannerDialogOpen;
  }
}
