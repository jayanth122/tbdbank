import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { FooterComponent } from '../footer/footer.component';

import { ThirdPartyQrComponent } from './third-party-qr.component';

describe('ThirdPartyQrComponent', () => {
  let component: ThirdPartyQrComponent;
  let fixture: ComponentFixture<ThirdPartyQrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ThirdPartyQrComponent,FooterComponent ],
      imports: [ HttpClientModule ],
 
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
