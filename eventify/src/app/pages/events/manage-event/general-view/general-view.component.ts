import { TotalExpenses } from './../../../../domain/model/event/totalexpenses.model.d';
import { Component, Input, OnInit } from '@angular/core';
import ViewItensType from './types';
import { MapComponent } from '../../../../components/map/map.component';
import { OrganizationInfo } from '../../../../domain/model/event/organizationInfo.model';
import { EventService } from '../../../../services/event/event.service';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-view',
  standalone: true,
  imports: [MapComponent],
  templateUrl: './general-view.component.html',
  styleUrl: './general-view.component.scss',
})
export class GeneralViewComponent implements OnInit {
  organizationData!: OrganizationInfo;
  createdOnFormatted!: string;
  totalExpenses!: string;

  constructor(
    private eventService: EventService,
    private activatedRoute: ActivatedRoute
  ) {}

  @Input() createDate!: string;
  @Input() startDate!: string;
  @Input() endDate!: string;
  @Input() userName!: string;
  @Input() participantsNumber!: string;
  @Input() status!: string;
  @Input() currentExpense!: string;

  generalViewExample: ViewItensType = {
    createDate: '2022-01-01',
    startDate: '2022-01-01',
    endDate: '2022-01-31',
    userName: 'John Doe',
    participantsNumber: '10',
    status: 'active',
    currentExpense: '1000.00',
  };

  async ngOnInit(): Promise<void> {
    let eventId = this.activatedRoute.snapshot.paramMap.get('id');
    eventId = eventId == null ? '-1' : eventId;
    const organizationResponse = await this.eventService.getOrganizationData(
      eventId
    );
    this.organizationData = organizationResponse;
    this.createdOnFormatted = new Date(
      this.organizationData.createdOn
    ).toLocaleDateString();
    const expensesResponse = await this.eventService.getTotalExpenses(eventId);
    this.totalExpenses = `${expensesResponse.totalExpanses.toFixed(2)}`;
  }
}
