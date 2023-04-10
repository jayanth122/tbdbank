import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UserAccountComponent } from './user-account.component';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { DataService } from '../../data.service';
import {Router, RouterModule} from '@angular/router';
import {RegistrationComponent} from "../registration/registration.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { RouterTestingModule } from '@angular/router/testing';
import {of} from "rxjs";
import {QrRequest} from "../../dto/QrRequest";
import {CommonModule} from "@angular/common";


describe('UserAccountComponent', () => {
  let component: UserAccountComponent;
  let fixture: ComponentFixture<UserAccountComponent>;
  let router: Router;
  let routerSpy: jasmine.SpyObj<Router>;
  let dataServiceSpy: jasmine.SpyObj<DataService>;
  let dataService: DataService;


  beforeEach(async () => {
    const dataServiceSpy = jasmine.createSpyObj<DataService>('DataService', ['getFirstName', 'getLastName', 'getAccountBalance', 'validateInterac', 'generateQr', 'updateSession', 'setPaymentQrImage', 'setPaymentQrPdf','isLoginValid']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
      await TestBed.configureTestingModule({
        declarations: [ RegistrationComponent,HeaderComponent, FooterComponent ],
        imports: [HttpClientTestingModule,RouterModule, ReactiveFormsModule, RouterTestingModule, CommonModule, FormsModule ],
        providers: [{ provide: DataService, useValue: dataServiceSpy },{provide: Router , useValue: routerSpy},DataService]
      })

    .compileComponents();

    fixture = TestBed.createComponent(UserAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router); // inject the Router object
    dataService = TestBed.inject(DataService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should navigate to the Interac page', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['validateInterac']);
    dataService.isLoginValid = true;
    localStorage.setItem('sessionId', 'testSessionId');
    const component = new UserAccountComponent(router, dataService);
    component.goToInterac();
    expect(router.navigate).toHaveBeenCalledWith(['interac']);
  });




  it('should set the first name without quotes', () => {
    const name = '"John"';
    component.setFirstName(name);
    expect(component.firstName).toBe('John');
  });

  it('should set the last name without quotes', () => {
    const name = '"Doe"';
    component.setLastName(name);
    expect(component.lastName).toBe('Doe');
  });
  it('should remove quotes from last name', () => {
    const name = '"Doe"';
    component.setLastName(name);
    expect(component.lastName).not.toContain('"');
  });

  it('should navigate to transaction page if session is valid', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['isLoginValid']);
    dataService.isLoginValid = true;
    localStorage.setItem('sessionId', 'testSessionId');
    const component = new UserAccountComponent(router, dataService);
    component.goToTransactions();
    expect(router.navigate).toHaveBeenCalledWith(['transaction']);
  });
  it('should navigate to login if session is invalid', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['isLoginValid']);
    dataService.isLoginValid = false;
    localStorage.removeItem('sessionId');
    const component = new UserAccountComponent(router, dataService);
    component.goToTransactions();
    expect(router.navigate).toHaveBeenCalledWith(['login']);
  });

  it('should navigate to login if session is invalid', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['isLoginValid']);
    dataService.isLoginValid = false;
    localStorage.removeItem('sessionId');
    const component = new UserAccountComponent(router, dataService);
    component.goToInterac();
    expect(router.navigate).toHaveBeenCalledWith(['login']);
  });


  it('should navigate to login if session is invalid', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['validateInterac', 'updateSession', 'isLoginValid', 'generateQr']);
    localStorage.setItem('sessionId', 'test');
    dataService.isLoginValid = false;
    dataService.validateInterac.and.returnValue(of({valid: true, sessionId: 'new-session-id'}));
    dataService.generateQr.and.returnValue(of({}));
    localStorage.removeItem('sessionId');
    const component = new UserAccountComponent(router, dataService);
    component.validateCustomerInterac();
    expect(router.navigate).toHaveBeenCalledWith(['login']);
  });
  it('should navigate to interac-register if interac is not valid', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['validateInterac', 'updateSession', 'isLoginValid']);
    localStorage.setItem('sessionId', 'test');
    dataService.isLoginValid = true;
    dataService.validateInterac.and.returnValue(of({ valid: false, sessionId: 'new-session-id' }));
    const component = new UserAccountComponent(router, dataService);
    component.validateCustomerInterac();
    expect(router.navigate).toHaveBeenCalledWith(['interac-register']);
  });
  it('should generate QR code and navigate to qr page if successful', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['generateQr', 'setPaymentQrImage', 'setPaymentQrPdf', 'updateSession']);
    localStorage.setItem('sessionId', 'test');
    const qrRequest = {sessionId: 'test'} as QrRequest;
    const response = {success: true, message: 'QR code generated successfully', sessionId: 'new-session-id', qrImage: 'qr-image', qrPdf: 'qr-pdf'};
    dataService.generateQr.and.returnValue(of(response));
    const component = new UserAccountComponent(router, dataService);
    component.generateQr();
    expect(dataService.setPaymentQrImage).toHaveBeenCalledWith('qr-image');
    expect(dataService.setPaymentQrPdf).toHaveBeenCalledWith('qr-pdf');
    expect(dataService.updateSession).toHaveBeenCalledWith(true, 'new-session-id');
    expect(router.navigate).toHaveBeenCalledWith(['qr']);
  });
});
