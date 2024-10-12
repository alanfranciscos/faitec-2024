import { Component } from '@angular/core';
import { LanguageComponent } from '../language/language.component';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [LanguageComponent],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss',
})
export class FooterComponent {
  subscribeToNewsletter() {
    alert('Subscribed to the newsletter!');
  }
}
