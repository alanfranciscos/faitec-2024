import { Component, forwardRef, Input } from '@angular/core';
import {
  ReactiveFormsModule,
  NG_VALUE_ACCESSOR,
  ControlValueAccessor,
} from '@angular/forms';
import { TooltipComponent } from '../tooltip/tooltip.component';

type InputTypes = 'text' | 'email' | 'password' | 'file' | 'number' | 'date';

@Component({
  selector: 'app-primary-input',
  standalone: true,
  imports: [ReactiveFormsModule, TooltipComponent],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PrimaryInputComponent),
      multi: true,
    },
  ],
  templateUrl: './primary-input.component.html',
  styleUrls: ['./primary-input.component.scss'],
})
export class PrimaryInputComponent implements ControlValueAccessor {
  @Input() type: InputTypes = 'text';
  @Input() placeholder: string = '';
  @Input() label: string = '';
  @Input() inputName: string = '';

  value: string | File | null = '';
  onChange: any = () => {};
  onTouched: any = () => {};

  onInput(event: Event) {
    const input = event.target as HTMLInputElement;
    const value =
      this.type === 'file'
        ? input.files
          ? input.files[0]
          : null
        : input.value;
    this.onChange(value);
  }

  writeValue(value: any): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {}
}
