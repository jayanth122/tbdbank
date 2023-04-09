import { ComponentFixture, TestBed,fakeAsync, tick } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing'; // add this import statement
import { ReactiveFormsModule } from '@angular/forms'; // add this import statement
import {Router} from "@angular/router";
import { RegistrationComponent } from './registration.component';
import { DataService } from '../../data.service';
import { of } from 'rxjs';
import { throwError } from 'rxjs';
import { By } from '@angular/platform-browser';
describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;
  let dataServiceSpy: jasmine.SpyObj<DataService>;
  let router: Router;
  let routerSpy: jasmine.SpyObj<Router>;
  let dataService : DataService;


  beforeEach(async () => {
    dataServiceSpy = jasmine.createSpyObj('DataService', ['sendRegistrationDetails','setVerificationImage','setVerificationPdf']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    await TestBed.configureTestingModule({
      declarations: [ RegistrationComponent,HeaderComponent, FooterComponent ],
      imports: [HttpClientTestingModule,RouterModule, ReactiveFormsModule ],
      providers: [{ provide: DataService, useValue: dataServiceSpy },{provide: Router , useValue: routerSpy}]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router); // inject the Router object
    dataService = TestBed.inject(DataService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

it('should initialize registrationForm on ngOnInit', () => {
  expect(component.registrationForm).toBeDefined();
});
  it('should map "Yes" to true and "No" to false', () => {
    expect(component.mapYesNoToBoolean('Yes')).toBeTrue();
    expect(component.mapYesNoToBoolean('No')).toBeFalse();
  });

it('should mark form as submitted when onSubmit is called', () => {
  // Set up form with required fields completed
  component.registrationForm.setValue({
    userName: 'try012',
    firstName: 'test',
    middleName:'test',
    lastName: 'test',
    dateOfBirth: '2000-01-01',
    email: 'test@test.com',
    password: 'password',
    countryCode: '1',
    mobileNumber: '5890123217',
    streetNumber: '123',
    gender: 'MALE',
    streetName: 'test',
    city: 'Waterloo',
    province: 'MB',
    postalCode: 'A1A 1A1',
    sinNumber: '4091432870',
    testAccount: 'Yes',
    unitNumber: '1'
  });
  // Mock DataService's sendRegistrationDetails method
  dataServiceSpy.sendRegistrationDetails.and.returnValue(of({ success: true, message: 'Success!' }));
  component.onSubmit();
  expect(component.submitted).toBeTrue();
  expect(routerSpy.navigate).toHaveBeenCalledWith(['verification']);
});


it('should call dataService.sendRegistrationDetails when onSubmit is called with valid form data', fakeAsync(() => {
  dataServiceSpy.sendRegistrationDetails.and.returnValue(of({}));
  // Set up form with required fields completed
  component.registrationForm.setValue({
    userName: 'testcase01',
    firstName: 'test',
    lastName: 'test',
    middleName:'test',
    dateOfBirth: '2000-01-01',
    email: 'test@test.com',
    password: 'password',
    countryCode: '1',
    mobileNumber: '1234567890',
    streetNumber: '123',
    gender: 'MALE',
    streetName: 'test',
    city: 'test',
    province: 'AB',
    postalCode: 'A1A 1A1',
    sinNumber: '6651237891',
    testAccount: 'No',
    unitNumber: '1'
  });
  // Set up router to return a dummy route
  component.onSubmit();
  tick();
  expect(dataServiceSpy.sendRegistrationDetails).toHaveBeenCalledWith(component.registrationForm.value);
   // assert that router.navigate was called with the expected argument
}));
  it('should reset the form and set submitted to false when onReset is called', () => {
    // set initial form values
    component.registrationForm.setValue({
      userName: 'john.doe',
      firstName: 'John',
      middleName: 'W',
      lastName: 'Doe',
      dateOfBirth: '1990-01-01',
      email: 'john.doe@test.com',
      password: 'password',
      countryCode: 'US',
      mobileNumber: '1234567890',
      streetNumber: '123',
      gender: 'male',
      unitNumber: '456',
      streetName: 'Main St',
      city: 'New York',
      province: 'NY',
      postalCode: '12345',
      sinNumber: '123456789',
      testAccount: 'true'
    });
    // simulate form submission
    component.submitted = true;
    // call onReset function
    component.onReset();
    // check if form fields are reset to expected values
    expect(component.registrationForm.value).toEqual({
      userName: null,
      firstName: null,
      middleName: null,
      lastName: null,
      dateOfBirth: null,
      email: null,
      password: null,
      countryCode: null,
      mobileNumber: null,
      streetNumber: null,
      gender: null,
      unitNumber: null,
      streetName: null,
      city: null,
      province: null,
      postalCode: null,
      sinNumber: null,
      testAccount: null
    });
    // check if submitted is set to false
    expect(component.submitted).toBe(false);
  });
});
