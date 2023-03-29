import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentQrComponent } from './payment-qr.component';

describe('PaymentQrComponent', () => {
  let component: PaymentQrComponent;
  let fixture: ComponentFixture<PaymentQrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentQrComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentQrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
