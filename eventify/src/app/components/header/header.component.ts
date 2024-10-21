import { Component, Input, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { TooltipComponent } from '../tooltip/tooltip.component';
import { DropdownlistComponent } from '../dropdownlist/dropdownlist.component';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user/user.service';
import { ProfileData } from '../../domain/model/event/profileData.model';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, TooltipComponent, DropdownlistComponent, CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  UserName!: string;
  UserImage!: string;
  @Input() ComponentName: string = '';
  @Input() textTooltip?: string;
  @Input() tooltipPosition: 'top' | 'bottom' | 'left' | 'right' = 'top';

  isOpen = false;
  openedByClick = false; // Nova variável para controlar o clique

  constructor(private router: Router, private userService: UserService) {}
  async ngOnInit(): Promise<void> {
    const userData = await this.userService.getProfileData();
    this.UserName = await userData.username;
    this.UserImage = await userData.imageData;

    if (!this.UserImage) {
      this.UserImage = '/assets/svg/logo.svg';
    }
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
    this.openedByClick = this.isOpen; // Se abriu por clique, mantém o estado.
  }

  openDropdown() {
    if (!this.openedByClick) {
      this.isOpen = true; // Abre apenas por hover se não foi clicado
    }
  }

  closeDropdown() {
    if (!this.openedByClick) {
      this.isOpen = false; // Fecha apenas se não foi clicado
    }
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/account/login']);
  }
}
