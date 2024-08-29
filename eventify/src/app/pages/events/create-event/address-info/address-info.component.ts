import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MapComponent } from '../../../../components/map/map.component';
import { HeaderComponent } from '../../../../components/header/header.component';

@Component({
  selector: 'app-address-info',
  standalone: true,
  imports: [RouterLink, MapComponent, HeaderComponent],
  templateUrl: './address-info.component.html',
  styleUrl: './address-info.component.scss',
})
export class AddressInfoComponent {}
