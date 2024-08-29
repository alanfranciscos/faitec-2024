import { Component } from '@angular/core';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterOutlet } from '@angular/router';
import { MainLayoutComponent } from '../../Layout/main-layout/main-layout.component';
import { MyEventsComponent } from '../event/my-events/my-events.component';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    MainLayoutComponent,
    MyEventsComponent,
    FooterComponent,
    RouterOutlet,
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent {}
