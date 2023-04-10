import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UpiPaymentsComponent } from './upi-payments.component';
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";
describe('UpiPaymentsComponent', () => {
  let component: UpiPaymentsComponent;
  let fixture: ComponentFixture<UpiPaymentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        UpiPaymentsComponent,
        HeaderComponent,
        FooterComponent
      ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpiPaymentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
