import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss',
})
export class CardComponent {
  @Input() title: string = 'titulo';
  @Input() description: string = 'descripcion';
  @Input() image: string = 'imagen';
  @Input() startData: string = 'startData';
  @Input() endData: string = 'endData';
  @Input() status: string = 'color';
}
