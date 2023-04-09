import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';
import {ReactiveFormsModule, Validators} from '@angular/forms';
import { FooterComponent } from '../footer/footer.component';
import { of, throwError } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { InteracRegisterComponent } from './interac-register.component';
import { DataService } from '../../data.service';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
describe('InteracRegisterComponent', () => {
  let component: InteracRegisterComponent;
  let fixture: ComponentFixture<InteracRegisterComponent>;
  let routerSpy: jasmine.SpyObj<Router>;
  let dataServiceSpy: jasmine.SpyObj<DataService>;
  let dataService: DataService;
  let router: Router;
  beforeEach(async () => {
    const dataServiceSpy = jasmine.createSpyObj<DataService>('DataService', ['getFirstName', 'getLastName', 'getAccountBalance', 'validateInterac', 'generateQr', 'updateSession', 'setPaymentQrImage', 'setPaymentQrPdf','isLoginValid','interacRegister','setIsLoginValid']);
    dataService = jasmine.createSpyObj('DataService', ['interacRegister', 'setIsLoginValid', 'isLoginValid']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    await TestBed.configureTestingModule({

      declarations: [ InteracRegisterComponent, HeaderComponent, FooterComponent ],
       imports: [ HttpClientModule,ReactiveFormsModule,RouterTestingModule.withRoutes([])],
      providers: [ FormBuilder, { provide: Router, useValue: routerSpy }, { provide: DataService, useValue: dataServiceSpy }, DataService]

    })
    .compileComponents();
    fixture = TestBed.createComponent(InteracRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router); // inject the Router object
    dataService = TestBed.inject(DataService);
  });
  afterEach(() => {
    localStorage.removeItem('sessionId');
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should navigate to login if session is invalid', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['isLoginValid']);
    dataService.isLoginValid = false;
    localStorage.removeItem('sessionId');
    component = new InteracRegisterComponent(router,dataService, new FormBuilder());
    expect(router.navigate).toHaveBeenCalledWith(['login']);
  });
  it('should mark the form as submitted on submit', () => {
    component.onSubmit();
    expect(component.submitted).toBeTrue();
  });
  it('should navigate to user-account on successful submission', () => {
    const response = { success: true, message: 'Registration successful', sessionId: 'new-session-id' };
    const dataService = jasmine.createSpyObj('DataService', ['interacRegister','setIsLoginValid']);
    dataService.interacRegister.and.returnValue(of(response));
    const emailInput = fixture.nativeElement.querySelector('input[type="email"]');
    const formBuilder = new FormBuilder();
    const component = new InteracRegisterComponent(router, dataService, formBuilder);
    component.interacRegisterForm = formBuilder.group({
      email: ['test@example.com', Validators.required],
    });
    component.onSubmit();
    expect(router.navigate).toHaveBeenCalledWith(['user-account']);
  });
});
