import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MapComponent } from '../../../../components/map/map.component';
import { HeaderComponent } from '../../../../components/header/header.component';
import { Coordinate } from '../../../../domain/model/event/eventLocalization.model';
import { CreateEventService } from '../create-event.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-address-info',
  standalone: true,
  imports: [RouterLink, MapComponent, HeaderComponent, FormsModule],
  templateUrl: './address-info.component.html',
  styleUrls: ['./address-info.component.scss'],
})
export class AddressInfoComponent {
  placeName: string = '';
  postalCode: string = '';
  state: string = '';
  city: string = '';
  neighborhood: string = '';
  street: string = '';
  number: string = '';
  complement: string = '';
  coordinates: Coordinate | null = null;

  constructor(private createEventService: CreateEventService) {}
  onCoordinatesChange(newCoordinates: Coordinate) {
    this.coordinates = newCoordinates;
  }
  onAddPayment() {
    const addressData = {
      placeName: this.placeName,
      postalCode: this.postalCode,
      state: this.state,
      city: this.city,
      neighborhood: this.neighborhood,
      street: this.street,
      number: this.number,
      complement: this.complement,
      coordinates: this.coordinates,
    };

    // Armazena os dados de endereço no serviço
    this.createEventService.setAddressData(addressData);
  }
}
