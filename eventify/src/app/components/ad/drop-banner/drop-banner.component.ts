import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-drop-bottom-banner',
  imports: [CommonModule],
  templateUrl: './drop-banner.component.html',

  styleUrl: './drop-banner.component.scss',
})
export class DropBannerComponent {
  isHidden = false; // Controla se o banner está visível ou escondido
  toggleBanner() {
    this.isHidden = !this.isHidden; // Alterna entre visível e escondido
  }
  hideBanner() {
    this.isHidden = true; // Muda o estado para esconder o banner
  }
}
