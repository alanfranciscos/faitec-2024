import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TooltipComponent } from '../tooltip/tooltip.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, TooltipComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  @Input() ComponentName!: string | '#';
  @Input() textTooltip?: string;
  @Input() tooltipPosition: 'top' | 'bottom' | 'left' | 'right' = 'top'; // Posição padrão
}
