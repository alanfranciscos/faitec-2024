import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { EventService } from '../../../../services/event/event.service';

@Component({
  selector: 'app-basic-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent],
  templateUrl: './basic-info.component.html',
  styleUrl: './basic-info.component.scss',
})
export class _BasicInfoComponent implements OnInit {
  backRoute!: string;
  nextRoute!: string;
  constructor(
    private activatedRoute: ActivatedRoute,
    private eventService: EventService
  ) {}
  async ngOnInit(): Promise<void> {
    let eventId = this.activatedRoute.snapshot.paramMap.get('id');
    eventId = eventId == null ? '-1' : eventId;
    this.backRoute = '/event/' + eventId;
    this.nextRoute = '/event/' + eventId + '/edit-event/address-info';
  }
}
