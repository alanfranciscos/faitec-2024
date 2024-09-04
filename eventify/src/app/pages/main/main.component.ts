import { Component } from '@angular/core';
import { MainSidebarComponent } from '../../components/sidebar/main-sidebar/main-sidebar.component';
import { MyEventsComponent } from '../events/my-events/my-events.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    MainSidebarComponent,
    MyEventsComponent,
    FooterComponent,
    RouterOutlet,
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent {}
