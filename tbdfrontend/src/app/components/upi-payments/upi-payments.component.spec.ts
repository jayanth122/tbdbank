import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UpiPaymentsComponent } from './upi-payments.component';
import { DataService } from '../../data.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";

describe('UpiPaymentsComponent', () => {
  let component: UpiPaymentsComponent;
  let fixture: ComponentFixture<UpiPaymentsComponent>;
  let dataService: DataService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpiPaymentsComponent, HeaderComponent, FooterComponent],
      imports: [ HttpClientTestingModule, RouterTestingModule ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpiPaymentsComponent);
    component = fixture.componentInstance;
    dataService = TestBed.inject(DataService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should upload image and call dataService.upiPayment', () => {
    spyOn(localStorage, 'getItem').and.returnValue('testSessionId');
    const file = new File(['test'], 'test.png', { type: 'image/png' });
    const mockReader = jasmine.createSpyObj('FileReader', ['readAsDataURL']);
    mockReader.onload = jasmine.createSpy('onload');
    spyOn(window as any, 'FileReader').and.returnValue(mockReader);
    const upiPaymentSpy = spyOn(dataService, 'upiPayment').and.returnValue(of({}));
    component.selectedFile = file;
    component.uploadImage();
    expect(upiPaymentSpy).toHaveBeenCalled();
  });

  it('should navigate to login page if session id is not present and login is not valid', () => {
    component.dataService.isLoginValid = false;
    spyOn(component.router, 'navigate');
    component.uploadImage();
    expect(component.router.navigate).toHaveBeenCalledWith(['login']);
  });


  it('should not navigate to login page if session id is present', () => {
    spyOn(localStorage, 'getItem').and.returnValue('somesessionid');
    component.dataService.isLoginValid = false;
    spyOn(component.router, 'navigate');
    component.uploadImage();
    expect(component.router.navigate).not.toHaveBeenCalledWith(['login']);
  });

  it('should not navigate to login page if login is valid', () => {
    spyOn(localStorage, 'getItem').and.returnValue('fakeSessionId');
    spyOn(component.router, 'navigate');
    component.dataService.isLoginValid = true;
    component.uploadImage();
    expect(component.router.navigate).not.toHaveBeenCalledWith(['login']);
  });

});
