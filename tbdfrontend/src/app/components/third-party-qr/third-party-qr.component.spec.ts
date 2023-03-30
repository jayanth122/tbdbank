import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThirdPartyQrComponent } from './third-party-qr.component';

describe('ThirdPartyQrComponent', () => {
  let component: ThirdPartyQrComponent;
  let fixture: ComponentFixture<ThirdPartyQrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ThirdPartyQrComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ThirdPartyQrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
