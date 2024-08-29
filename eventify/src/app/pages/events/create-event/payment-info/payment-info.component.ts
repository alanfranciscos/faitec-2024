import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';

@Component({
  selector: 'app-payment-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent],
  templateUrl: './payment-info.component.html',
  styleUrl: './payment-info.component.scss',
})
export class PaymentInfoComponent {}
