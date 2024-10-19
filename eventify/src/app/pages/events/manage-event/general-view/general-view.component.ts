import { EventLocation } from './../../../../domain/model/event/eventLocalization.model.d';
import { TotalExpenses } from './../../../../domain/model/event/totalexpenses.model.d';
import { Component, Input, OnInit } from '@angular/core';
import { MapComponent } from '../../../../components/map/map.component';
import { OrganizationInfo } from '../../../../domain/model/event/organizationInfo.model';
import { EventService } from '../../../../services/event/event.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { EventDate } from '../../../../domain/model/event/eventDate.model';
import { PrimaryInputComponent } from '../../../../components/primary-input/primary-input.component';
import { ButtonComponent } from '../../../../components/button/button.component';
import { ConfirmationDialogComponent } from '../../../../components/confirmation-dialog/confirmation-dialog.component';
import { CommonModule } from '@angular/common';
import { DeleteEventService } from '../../../../services/event/delete-event.service';
@Component({
  selector: 'app-view',
  standalone: true,
  imports: [
    MapComponent,
    PrimaryInputComponent,
    ButtonComponent,
    RouterLink,
    ConfirmationDialogComponent,
    CommonModule,
  ],
  templateUrl: './general-view.component.html',
  styleUrl: './general-view.component.scss',
})
export class GeneralViewComponent implements OnInit {
  constructor(
    private eventService: EventService,
    private activatedRoute: ActivatedRoute,
    private deleteEvent: DeleteEventService,
    private router: Router
  ) {}

  @Input() createDate!: string;
  @Input() startDate!: string;
  @Input() endDate!: string;
  @Input() userName!: string;
  @Input() participantsNumber!: string;
  @Input() status!: string;
  @Input() currentExpense!: string;

  organizationData!: OrganizationInfo;
  createdOnFormatted!: string;
  totalExpenses!: TotalExpenses;
  eventDate!: EventDate;
  eventLocation!: EventLocation;
  popUpInfo!: string;
  route!: string;

  async ngOnInit(): Promise<void> {
    let eventId = this.activatedRoute.snapshot.paramMap.get('id');
    eventId = eventId == null ? '-1' : eventId;
    this.route = '/event/' + eventId + '/edit-event/basic-info';

    const organizationResponse = await this.eventService.getOrganizationData(
      eventId
    );

    this.organizationData = organizationResponse;
    this.organizationData.createdOn = new Date(
      this.organizationData.createdOn
    ).toLocaleDateString();

    const totalExpenses = await this.eventService.getTotalExpenses(eventId);
    this.totalExpenses = totalExpenses;

    const eventDate = await this.eventService.getEventDate(eventId);
    this.eventDate = eventDate;

    eventDate.startDate = new Date(eventDate.startDate).toLocaleDateString();
    eventDate.endDate = new Date(eventDate.endDate).toLocaleDateString();

    const eventLocation = await this.eventService.getEventLocation(eventId);
    this.eventLocation = eventLocation;

    this.popUpInfo = `${eventLocation.locationName} - ${eventLocation.city}`;
  }
  showDialog = false;

  openDialog() {
    this.showDialog = true;
  }

  async handleConfirm(id: number) {
    this.showDialog = false;
    this.onDeleteEvent(id);
    this.router.navigate(['/']);
  }

  handleCancel() {
    this.showDialog = false;
  }

  async onDeleteEvent(id: number) {
    try {
      await this.deleteEvent.deleteEvent(id);

      //   this.eventId,
      //   this.offset,
      //   this.limit
      // );
    } catch (error) {
      console.log(error);
    }
  }
}
