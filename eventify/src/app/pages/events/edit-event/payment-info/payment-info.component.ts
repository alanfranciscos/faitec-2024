import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { EventService } from '../../../../services/event/event.service';

@Component({
  selector: 'app-payment-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent],
  templateUrl: './payment-info.component.html',
  styleUrl: './payment-info.component.scss',
})
export class _PaymentInfoComponent implements OnInit {
  backRoute!: string;
  nextRoute!: string;
  constructor(
    private activatedRoute: ActivatedRoute,
    private eventService: EventService
  ) {}

  ngOnInit(): void {
    let eventId = this.activatedRoute.snapshot.paramMap.get('id');
    eventId = eventId == null ? '-1' : eventId;
    this.backRoute = '/event/' + eventId + '/edit-event/address-info';
    this.nextRoute = '/event/' + eventId;
  }
}
