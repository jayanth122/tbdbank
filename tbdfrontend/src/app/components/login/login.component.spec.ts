// import {ComponentFixture, TestBed, tick, waitForAsync} from '@angular/core/testing';
// import { RouterTestingModule } from '@angular/router/testing';
// import { HttpClientTestingModule } from '@angular/common/http/testing';
// import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
// import { HeaderComponent } from '../header/header.component';
// import { FooterComponent } from '../footer/footer.component';
// import { LoginComponent } from './login.component';
// import {of} from "rxjs";
// import {RegistrationComponent} from "../registration/registration.component";
// import {DataService} from "../../data.service";
// import {Router, RouterModule} from "@angular/router";
// import {StatementComponent} from "../statement/statement.component";
// import {InteracRegisterComponent} from "../interac-register/interac-register.component";
//
//
// describe('LoginComponent', () => {
//   let component: LoginComponent;
//   let fixture: ComponentFixture<LoginComponent>;
//   let dataServiceSpy: jasmine.SpyObj<DataService>;
//   let router: Router;
//   let routerSpy: jasmine.SpyObj<Router>;
//   let dataService : DataService;
//   let formBuilder: FormBuilder;
//
//   beforeEach(async () => {
//     dataServiceSpy = jasmine.createSpyObj('DataService', ['sendLoginDetails', 'setSessionValues', 'setIsLoginValid', 'fetchCustomerDetails']),
//     routerSpy = jasmine.createSpyObj('Router', ['navigate']);
//     formBuilder = new FormBuilder();
//     await TestBed.configureTestingModule({
//       declarations: [ LoginComponent,HeaderComponent, FooterComponent ],
//       imports: [HttpClientTestingModule,RouterModule, ReactiveFormsModule ],
//       providers: [{ provide: DataService, useValue: dataServiceSpy },{provide: Router , useValue: routerSpy},DataService,{ provide: FormBuilder, useValue: formBuilder}]
//     })
//       .compileComponents();
//
//     fixture = TestBed.createComponent(LoginComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//     router = TestBed.inject(Router); // inject the Router object
//     dataService = TestBed.inject(DataService);
//
//   });
//
//   beforeEach(() => {
//     fixture = TestBed.createComponent(LoginComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });
//
//   it('should create the login component', () => {
//     expect(component).toBeTruthy();
//   });
//
//   it('should initialize the login form', () => {
//     expect(component.loginForm).toBeDefined();
//     expect(component.loginForm.controls['userName'].value).toEqual('');
//     expect(component.loginForm.controls['password'].value).toEqual('');
//   });
//
//   it('should validate the login form', () => {
//     expect(component.loginForm.valid).toBeFalsy();
//     component.loginForm.controls['userName'].setValue('john.doe');
//     component.loginForm.controls['password'].setValue('password');
//     expect(component.loginForm.valid).toBeTruthy();
//   });
//   /*it('should navigate to user account page if session ID or valid login exists', () => {
//     const router = jasmine.createSpyObj('Router', ['navigate']);
//     const dataService = jasmine.createSpyObj('DataService', ['isLoginValid']);
//     dataService.isLoginValid.and.returnValue(true);
//     component.ngOnInit();
//     expect(router.navigate).toHaveBeenCalledWith(['user-account']);
//   });
// */
//   it('should navigate to user account page if session ID or valid login exists', () => {
//     const router = jasmine.createSpyObj('Router', ['navigate']);
//     const dataService = jasmine.createSpyObj('DataService', ['getSessionId', 'isLoginValid']);
//     dataService.getSessionId.and.returnValue('12345');
//     dataService.isLoginValid.and.returnValue(true);
//     component = new LoginComponent(router,dataService, new FormBuilder());
//     component.ngOnInit();
//     expect(router.navigate).toHaveBeenCalledWith(['user-account']);
//   });
//
//
//   it('should set submitted to true and return if form is invalid', () => {
//     const dataService = jasmine.createSpyObj('DataService', ['sendLoginDetails', 'setSessionValues', 'fetchCustomerDetails', 'setIsLoginValid']);
//     dataService.isLoginValid = true;
//     spyOn(console, 'log');
//     component.onSubmit();
//     expect(component.submitted).toBeTruthy();
//     expect(console.log).not.toHaveBeenCalled();
//     expect(dataService.sendLoginDetails).not.toHaveBeenCalled();
//   });
//
//
//   /*it('should set session values and navigate to user-account if login is successful and decodedAccess is CUSTOMER', fakeAsync(() => {
//     // Arrange
//     const dataService = TestBed.inject(DataService);
//     spyOn(dataService, 'sendLoginDetails').and.returnValue(of({
//       success: true,
//       firstName: 'Test',
//       lastName: 'User',
//       encodedAccess: 'Q1VTVE9NX05BTUU=' // base64-encoded "CUSTOMER"
//     }));
//     spyOn(dataService, 'setSessionValues');
//     spyOn(dataService, 'fetchCustomerDetails').and.returnValue(of({}));
//     spyOn(dataService, 'setIsLoginValid');
//     const router = TestBed.inject(Router);
//     spyOn(router, 'navigate');
//     const loginForm = component.loginForm;
//     loginForm.controls['userName'].setValue('testuser');
//     loginForm.controls['password'].setValue('password');
//
//     // Act
//     component.onSubmit();
//     tick();
//
//     // Assert
//     expect(dataService.sendLoginDetails).toHaveBeenCalledWith({
//       userName: 'testuser',
//       password: 'password'
//     });
//     expect(localStorage.getItem('userName')).toEqual('testuser');
//     expect(localStorage.getItem('firstName')).toEqual('"Test"');
//     expect(localStorage.getItem('lastName')).toEqual('"User"');
//     expect(dataService.setSessionValues).toHaveBeenCalledWith('testuser', '12345');
//     expect(dataService.setIsLoginValid).toHaveBeenCalledWith(true, '12345');
//     expect(dataService.fetchCustomerDetails).toHaveBeenCalled();
//     expect(router.navigate).toHaveBeenCalledWith(['user-account']);
//   }));
// *//*
//
//   it('should navigate to registration page when goToReg method is called', () => {
//     const router = jasmine.createSpyObj('Router', ['navigate']);
//     const dataService = jasmine.createSpyObj('DataService', ['setSessionValues', 'setIsLoginValid','sendLoginDetails']);
//     component = new LoginComponent(router, dataService, new FormBuilder());
//
//     component.goToReg();
//
//     expect(router.navigate).toHaveBeenCalledWith(['registration']);
//   });
//
//
//
//   it('should navigate to user-account and call fetchCustomerDetails on successful login for a customer', () => {
//     const mockData = {
//       success: true,
//       firstName: 'John',
//       lastName: 'Doe',
//       uniqueSessionId: '123456789',
//       encodedAccess: 'Q1VTVE9NRVI=' // 'CUSTOMER' encoded in base64
//     };
//     const router = jasmine.createSpyObj('Router', ['navigate']);
//     const dataService = jasmine.createSpyObj('DataService', ['setSessionValues', 'setIsLoginValid', 'sendLoginDetails', 'fetchCustomerDetails']);
//     const loginForm = new FormBuilder().group({
//       userName: ['testuser'],
//       password: ['testpassword']
//     });
//     component = new LoginComponent(router, dataService, new FormBuilder());
//
//     spyOn(localStorage, 'setItem').and.callThrough();
//
//
//
//     component.loginForm = loginForm;
//     component.onSubmit();
//
//     spyOn(dataService, 'fetchCustomerDetails').and.returnValue(of({}));
//     spyOn(dataService, 'sendLoginDetails').and.returnValue(of(mockData)); // return mockData as Observable
//     expect(dataService.sendLoginDetails).toHaveBeenCalledWith(component.loginForm.value);
//     expect(localStorage.setItem).toHaveBeenCalledWith('userName', 'testuser');
//     expect(localStorage.setItem).toHaveBeenCalledWith('firstName', JSON.stringify('John'));
//     expect(localStorage.setItem).toHaveBeenCalledWith('lastName', JSON.stringify('Doe'));
//     expect(dataService.setSessionValues).toHaveBeenCalledWith('testuser', '123456789');
//     expect(dataService.setIsLoginValid).toHaveBeenCalledWith(true, '123456789');
//     expect(dataService.fetchCustomerDetails).toHaveBeenCalled(); // expect the method to have been called
//     expect(router.navigate).toHaveBeenCalledWith(['user-account']);
//   });
// */
//
//
//   /*it('should call sendLoginDetails() on form submission', () => {
//     const mockData = {
//       success: true,
//       firstName: 'John',
//       lastName: 'Doe',
//       uniqueSessionId: '123456789',
//       encodedAccess: 'Q1VTVE9NRVI=' // 'CUSTOMER' encoded in base64
//     };
//     //const router = jasmine.createSpyObj('Router', ['navigate']);
//    // const dataService = jasmine.createSpyObj('DataService', ['setSessionValues', 'setIsLoginValid', 'sendLoginDetails', 'fetchCustomerDetails']);
//     const loginForm = new FormBuilder().group({
//       userName: ['testuser'],
//       password: ['testpassword']
//     });
//     component = new LoginComponent(router, dataService, new FormBuilder());
//
//     spyOn(localStorage, 'setItem').and.callThrough();
//     spyOn(dataService, 'sendLoginDetails').and.returnValue(of(mockData)); // return mockData as Observable
//     spyOn(dataService, 'fetchCustomerDetails').and.returnValue(of({}));
//
//     component.loginForm = loginForm;
//     component.onSubmit();
//
//     expect(dataService.sendLoginDetails).toHaveBeenCalledWith(component.loginForm.value);
//   });
// */
//
//
// })
