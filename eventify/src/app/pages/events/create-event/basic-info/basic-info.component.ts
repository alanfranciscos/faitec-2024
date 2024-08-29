import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';

@Component({
  selector: 'app-basic-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent],
  templateUrl: './basic-info.component.html',
  styleUrl: './basic-info.component.scss',
})
export class BasicInfoComponent {}
