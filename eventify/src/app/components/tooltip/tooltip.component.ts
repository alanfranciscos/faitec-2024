import { CommonModule } from '@angular/common';
import { Component, Input, HostListener } from '@angular/core';

@Component({
  selector: 'app-tooltip',
  imports: [CommonModule],
  standalone: true,
  templateUrl: './tooltip.component.html',
  styleUrls: ['./tooltip.component.scss'],
})
export class TooltipComponent {
  @Input() tooltipText: string = '';
  @Input() position: 'top' | 'bottom' | 'left' | 'right' = 'top';
  visible: boolean = false;

  @HostListener('mouseenter') onMouseEnter() {
    this.visible = true;
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.visible = false;
  }

  get tooltipClass() {
    return `tooltip-${this.position}`;
  }
}
