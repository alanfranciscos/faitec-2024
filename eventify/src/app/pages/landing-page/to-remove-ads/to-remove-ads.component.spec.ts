import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToRemoveAdsComponent } from './to-remove-ads.component';

describe('ToRemoveAdsComponent', () => {
  let component: ToRemoveAdsComponent;
  let fixture: ComponentFixture<ToRemoveAdsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ToRemoveAdsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ToRemoveAdsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
