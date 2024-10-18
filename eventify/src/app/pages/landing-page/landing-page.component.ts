import { Component } from '@angular/core';
import { NavbarComponent } from './navbar/navbar.component';
import { LandingPageMainComponent } from './main/main.component';
import { LandingPageFooterComponent } from './footer/footer.component';
import { LandingPageAboutComponent } from './about/about.component';
import { LandingPageUsageComponent } from './usage/usage.component';
import { ContactUsComponent } from './contact-us/contact-us.component';

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [
    NavbarComponent,
    LandingPageMainComponent,
    LandingPageFooterComponent,
    LandingPageAboutComponent,
    LandingPageUsageComponent,
    ContactUsComponent,
  ],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss',
})
export class LandingPageComponent {}
