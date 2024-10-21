import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss',
})
export class CardComponent implements OnInit {
  @Input() title: string = 'titulo';
  @Input() description: string = 'descripcion';
  @Input() image: string = '/assets/svg/logo.svg';
  @Input() startData: string = 'startData';
  @Input() endData: string = 'endData';
  @Input() status: string = 'color';
  @Input() url!: string;

  ngOnInit(): void {
    if (!this.image) {
      this.image = '/assets/svg/logo.svg';
    }
  }
}
