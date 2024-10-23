import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateEventService } from '../create-event.service';

@Component({
  selector: 'app-basic-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent, CommonModule, FormsModule],
  templateUrl: './basic-info.component.html',
  styleUrls: ['./basic-info.component.scss'],
})
export class BasicInfoComponent {
  constructor(private createEventService: CreateEventService) {}
  eventName: string = '';
  eventDescription: string = '';
  startDate: string = '';
  finishDate: string = '';
  eventImage: string | null | File = '/assets/svg/logo.svg';
  showImage: string = '/assets/svg/logo.svg';

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.eventImage = file;

      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        const imageData = e.target?.result as string;
        this.showImage = imageData;
      };

      reader.readAsDataURL(file);
    }
  }
  onRemoveImage() {
    this.eventImage = '/assets/svg/logo.svg';
    this.showImage = '/assets/svg/logo.svg';
  }

  onAddAddress() {
    const eventBasicData = {
      name: this.eventName,
      description: this.eventDescription,
      startDate: this.startDate,
      finishDate: this.finishDate,
      image: this.eventImage,
    };

    this.createEventService.setBasicInfoData(eventBasicData);
  }
}
