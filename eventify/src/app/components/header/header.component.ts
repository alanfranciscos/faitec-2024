import { Component, Input } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { TooltipComponent } from '../tooltip/tooltip.component';
import { DropdownlistComponent } from '../dropdownlist/dropdownlist.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, TooltipComponent, DropdownlistComponent, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  constructor(private router: Router) {}
  @Input() ComponentName!: string | '#';
  @Input() textTooltip?: string;
  @Input() tooltipPosition: 'top' | 'bottom' | 'left' | 'right' = 'top';
  isOpen = false;

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectOption(option: string) {
    this.isOpen = false;
  }
  logout() {
    localStorage.clear();
    this.router.navigate(['/account/login']); // Redireciona para a tela de login
  }
}
