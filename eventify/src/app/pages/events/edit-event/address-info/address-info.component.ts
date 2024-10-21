import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { MapComponent } from '../../../../components/map/map.component';
import { Coordinate } from '../../../../domain/model/event/eventLocalization.model';
import { EventService } from '../../../../services/event/event.service';
import { FormsModule } from '@angular/forms';
import { EventData } from '../../../../domain/model/event/eventData.model';
import { EditEventService } from '../edit-event.service';

@Component({
  selector: 'app-address-info',
  standalone: true,
  imports: [RouterLink, MapComponent, HeaderComponent, FormsModule],
  templateUrl: './address-info.component.html',
  styleUrls: ['./address-info.component.scss'], // Corrigi de styleUrl para styleUrls
})
export class _AddressInfoComponent implements OnInit {
  eventData!: EventData;
  backRoute!: string;
  nextRoute!: string;
  coordinates: Coordinate | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private eventService: EventService,
    private editEventService: EditEventService
  ) {}

  async ngOnInit(): Promise<void> {
    let eventId = this.activatedRoute.snapshot.paramMap.get('id');
    eventId = eventId == null ? '-1' : eventId;
    this.backRoute = '/event/' + eventId + '/edit-event/basic-info';
    this.nextRoute = '/event/' + eventId + '/edit-event/payment-info';
    this.eventData = await this.eventService.getEventData(Number(eventId));
  }

  onCoordinatesChange(newCoordinates: Coordinate) {
    this.coordinates = newCoordinates;
  }

  onAddPayment() {
    const addressData = {
      placeName: this.eventData.local_name,
      postalCode: this.eventData.cep_address,
      state: this.eventData.state_address,
      city: this.eventData.city_address,
      neighborhood: this.eventData.neighborhood_address,
      street: this.eventData.street_address,
      number: this.eventData.number_address,
      complement: this.eventData.complement_address,
      coordinates: this.coordinates,
    };
    console.log(addressData);
    this.editEventService.setAddressData(addressData);
  }

  onLocalNameChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.eventData.local_name = target.value;
  }

  onCepAddressChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.eventData.cep_address = target.value;
  }

  onStateAddressChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.eventData.state_address = target.value;
  }

  onCityAddressChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.eventData.city_address = target.value;
  }

  onNeighborhoodAddressChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.eventData.neighborhood_address = target.value;
  }

  onStreetAddressChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.eventData.street_address = target.value;
  }

  onNumberAddressChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.eventData.number_address = Number(target.value);
  }

  onComplementAddressChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.eventData.complement_address = target.value;
  }
}
