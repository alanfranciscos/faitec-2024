import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { ButtonComponent } from '../../button/button.component';

@Component({
  selector: 'app-full-banner',
  standalone: true,
  imports: [ButtonComponent, RouterOutlet],
  templateUrl: './full-banner.component.html',
  styleUrls: ['./full-banner.component.scss'],
})
export class FullBannerComponent {
  @Input() dialog_title?: string;
  @Input() dialog_placeholder?: string;
  @Output() close = new EventEmitter<void>();

  closeIcon: string | number = 5; // Inicia o contador em 5
  intervalId: any;

  constructor(private router: Router) {}

  ngOnInit() {
    // Inicia a contagem regressiva
    this.intervalId = setInterval(() => {
      if (typeof this.closeIcon === 'number' && this.closeIcon > 1) {
        this.closeIcon--;
      } else {
        this.closeIcon = '×'; // Muda para "x" após 5 segundos
        clearInterval(this.intervalId); // Para o intervalo
      }
    }, 1000);
  }

  closeDialog() {
    // Só fecha o diálogo quando o ícone for "×"
    if (this.closeIcon === '×') {
      this.close.emit();
    }
  }
}
