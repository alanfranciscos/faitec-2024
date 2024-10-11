import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-dropdownlist',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dropdownlist.component.html',
  styleUrl: './dropdownlist.component.scss',
})
export class DropdownlistComponent {
  isOpen = false;
  options = ['Opção 1', 'Opção 2', 'Opção 3'];
  selectedOption: string | null = null;

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectOption(option: string) {
    this.selectedOption = option;
    this.isOpen = false;
  }
}
