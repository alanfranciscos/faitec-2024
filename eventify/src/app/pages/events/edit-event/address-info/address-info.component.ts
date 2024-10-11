import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { MapComponent } from '../../../../components/map/map.component';
import { Coordinate } from '../../../../domain/model/event/eventLocalization.model';
import { EventService } from '../../../../services/event/event.service';

@Component({
  selector: 'app-address-info',
  standalone: true,
  imports: [RouterLink, MapComponent, HeaderComponent],
  templateUrl: './address-info.component.html',
  styleUrl: './address-info.component.scss',
})
export class _AddressInfoComponent implements OnInit {
  backRoute!: string;
  nextRoute!: string;
  constructor(
    private activatedRoute: ActivatedRoute,
    private eventService: EventService
  ) {}
  ngOnInit(): void {
    let eventId = this.activatedRoute.snapshot.paramMap.get('id');
    eventId = eventId == null ? '-1' : eventId;
    this.backRoute = '/event/' + eventId + '/edit-event/basic-info';
    this.nextRoute = '/event/' + eventId + '/edit-event/payment-info';
  }

  onCoordinatesChange(newCoordinates: Coordinate) {
    console.log('Novas coordenadas:', newCoordinates);
  }
}
