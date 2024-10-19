import {
  CreateEventServiceApi,
  EventInput,
} from './../../../../services/event/create-event.service-api';
import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { FormsModule } from '@angular/forms';
import { CreateEventService } from '../create-event.service';
@Component({
  selector: 'app-payment-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent, FormsModule],
  templateUrl: './payment-info.component.html',
  styleUrls: ['./payment-info.component.scss'],
})
export class PaymentInfoComponent {
  constructor(
    private createEventService: CreateEventService,
    private createEventServiceApi: CreateEventServiceApi,
    private router: Router
  ) {}

  pixKey: string = '';
  basicData: any;
  addressData: any;

  async onSubmitEvent() {
    this.basicData = this.createEventService.getBasicInfoData();
    this.addressData = this.createEventService.getAddressData();
    this.pixKey = this.createEventService.getPaymentData();

    console.log(this.basicData);
    console.log(this.addressData);
    console.log(this.pixKey);

    let lat = null;
    let long = null;

    if (this.addressData?.coordinates?.lat) {
      lat = this.addressData.coordinates;
      long = this.addressData.coordinates.lng;
    }

    const eventInput: EventInput = {
      eventname: this.basicData.name,
      eventdescription: this.basicData.description,

      local_name: this.addressData.placeName,
      cep_address: this.addressData.postalCode,
      state_address: this.addressData.state,
      city_address: this.addressData.city,
      neighborhood_address: this.addressData.neighborhood,
      number_address: this.addressData.number,
      street_address: this.addressData.street,
      complement_address: this.addressData.complement,
      latitude: lat,
      longitude: long,
      date_start: this.basicData.startDate,
      date_end: this.basicData.finishDate,
      stage: this.basicData.stage,
      pix_key: this.pixKey,
    };

    console.log(eventInput);

    const response = await this.createEventServiceApi.createEvent(
      eventInput,
      this.basicData.image
    );

    this.router.navigate(['/']);
  }
}
