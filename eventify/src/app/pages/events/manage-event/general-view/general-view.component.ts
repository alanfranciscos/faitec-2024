import { Component, Input } from '@angular/core';
import ViewItensType from './types';
import { MapComponent } from '../../../../components/map/map.component';
@Component({
  selector: 'app-view',
  standalone: true,
  imports: [MapComponent],
  templateUrl: './general-view.component.html',
  styleUrl: './general-view.component.scss',
})
export class GeneralViewComponent {
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
}
