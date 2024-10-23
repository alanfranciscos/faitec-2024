import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { EventService } from '../../../../services/event/event.service';
import { EventData } from '../../../../domain/model/event/eventData.model';
import { EditEventService } from '../edit-event.service';
import { EventInput } from '../../../../services/event/create-event.service-api';
import { UpdateEventService } from '../../../../services/event/update-event.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent, FormsModule],
  templateUrl: './payment-info.component.html',
  styleUrl: './payment-info.component.scss',
})
export class _PaymentInfoComponent implements OnInit {
  eventData!: EventData;
  backRoute!: string;

  nextRoute!: string;
  eventId!: string;
  basicData: any;
  addressData: any;

  pixKey!: string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private eventService: EventService,
    private editEventService: EditEventService,
    private updateEventService: UpdateEventService,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    let eventIdRoute = this.activatedRoute.snapshot.paramMap.get('id');
    this.eventId = eventIdRoute == null ? '-1' : eventIdRoute;
    this.backRoute = '/event/' + eventIdRoute + '/edit-event/address-info';
    this.nextRoute = '/event/' + eventIdRoute;
    this.eventData = await this.eventService.getEventData(Number(eventIdRoute));
  }

  private formatDate(date: any) {
    const startDate = new Date(date);
    const year = startDate.getFullYear();
    const month = String(startDate.getMonth() + 1).padStart(2, '0');
    const day = String(startDate.getDate()).padStart(2, '0');

    const formattedDate = `${year}-${month}-${day}`;

    return formattedDate;
  }

  async onSubmitEvent() {
    this.basicData = this.editEventService.getBasicInfoData();
    this.addressData = this.editEventService.getAddressData();
    this.pixKey = this.editEventService.getPaymentData();

    let lat = null;
    let long = null;

    if (this.addressData?.coordinates?.lat) {
      lat = this.addressData.coordinates.lat;
      long = this.addressData.coordinates.lng;
    }

    const eventInput: EventInput = {
      id: this.eventId,
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
      date_start: this.formatDate(this.basicData.startDate),
      date_end: this.formatDate(this.basicData.finishDate),
      stage: this.basicData.stage,
      pix_key: this.pixKey,
    };

    const response = await this.updateEventService.updateEvent(
      eventInput,
      this.basicData.image
    );
    window.location.reload();
  }
}
