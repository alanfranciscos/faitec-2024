import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CreateEventService } from './create-event.service';

@Component({
  selector: 'app-create-event',
  standalone: true,
  imports: [RouterModule],

  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss'],
})
export class CreateEventComponent {}
