import { Component, Input, OnInit } from '@angular/core';
import { Data } from '@angular/router';

@Component({
  selector: 'app-member-card',
  standalone: true,
  imports: [],
  templateUrl: './member-card.component.html',
  styleUrl: './member-card.component.scss',
})
export class MemberCardComponent implements OnInit {
  @Input() name!: string;
  @Input() roleParticipate!: string;
  @Input() image?: string;
  @Input() aceptedAt!: string;

  ngOnInit(): void {
    if (!this.image) {
      this.image = '/assets/svg/logo.svg';
    }
  }
}
