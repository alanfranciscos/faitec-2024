import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropBannerComponent } from './drop-banner.component';

describe('DropBannerComponent', () => {
  let component: DropBannerComponent;
  let fixture: ComponentFixture<DropBannerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DropBannerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DropBannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
