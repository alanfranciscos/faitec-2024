import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { EventService } from '../../../../services/event/event.service';
import { EventData } from '../../../../domain/model/event/eventData.model';
import { FormsModule } from '@angular/forms';
import { EditEventService } from '../edit-event.service';

@Component({
  selector: 'app-basic-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent, FormsModule],
  templateUrl: './basic-info.component.html',
  styleUrl: './basic-info.component.scss',
})
export class _BasicInfoComponent implements OnInit {
  eventData!: EventData;
  eventImage!: string;
  parsedEventStartData!: string;
  parsedEventFinishData!: string;
  backRoute!: string;
  nextRoute!: string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private eventService: EventService,
    private editEventService: EditEventService
  ) {}
  async ngOnInit(): Promise<void> {
    let eventId = this.activatedRoute.snapshot.paramMap.get('id');
    eventId = eventId == null ? '-1' : eventId;
    this.backRoute = '/event/' + eventId;
    this.nextRoute = '/event/' + eventId + '/edit-event/address-info';

    this.eventData = await this.eventService.getEventData(Number(eventId));
    this.eventImage = await this.eventService.getEventDataImage(
      Number(eventId)
    );

    this.parsedEventStartData = this.eventData.date_start
      .toString()
      .split('T')[0];
    this.parsedEventFinishData = this.eventData.date_start
      .toString()
      .split('T')[0];
  }

  onAddAddress() {
    const eventBasicData = {
      name: this.eventData.eventname,
      description: this.eventData.eventdescription,
      startDate: this.eventData.date_start,
      finishDate: this.eventData.date_end,
      image: this.eventImage, // A imagem está em base64
    };

    this.editEventService.setBasicInfoData(eventBasicData);
  }
}
