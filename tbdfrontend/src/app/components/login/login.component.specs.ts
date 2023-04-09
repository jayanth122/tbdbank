 import { async, ComponentFixture, TestBed } from '@angular/core/testing';
 import { LoginComponent } from './login.component';
 import { RouterTestingModule } from '@angular/router/testing';
 import { ReactiveFormsModule } from '@angular/forms';
 import { DataService } from '../../data.service';
 import { HttpClientTestingModule } from '@angular/common/http/testing';
 import { of } from 'rxjs';

 describe('LoginComponent', () => {
   let component: LoginComponent;
   let fixture: ComponentFixture<LoginComponent>;
   let dataService: DataService;

   beforeEach(async(() => {
     TestBed.configureTestingModule({
       declarations: [ LoginComponent ],
       imports: [
         RouterTestingModule,
         ReactiveFormsModule,
         HttpClientTestingModule
       ],
       providers: [DataService]
     })
       .compileComponents();
   }));

  beforeEach(() => {
     fixture = TestBed.createComponent(LoginComponent);
     component = fixture.componentInstance;
     dataService = TestBed.inject(DataService);
     fixture.detectChanges();
   });

   it('should create', () => {
     expect(component).toBeTruthy();
   });
//
//   it('should navigate to user-account if user is already logged in', () => {
//     spyOn(localStorage, 'getItem').and.returnValue('sessionId');
//     spyOn(dataService, 'isLoginValid').and.returnValue(true);
//     const navigateSpy = spyOn(component.router, 'navigate');
//     component.ngOnInit();
//     expect(navigateSpy).toHaveBeenCalledWith(['user-account']);
//   });
//
//   it('should initialize the form with the required fields', () => {
//     component.ngOnInit();
//     expect(component.loginForm.contains('userName')).toBeTruthy();
//     expect(component.loginForm.contains('password')).toBeTruthy();
//   });
//
//   it('should show validation errors for empty fields', () => {
//     component.loginForm.controls['userName'].setValue('');
//     component.loginForm.controls['password'].setValue('');
//     expect(component.loginForm.controls['userName'].valid).toBeFalsy();
//     expect(component.loginForm.controls['password'].valid).toBeFalsy();
//   });
//
//   it('should show validation error for invalid password', () => {
//     component.loginForm.controls['userName'].setValue('test');
//     component.loginForm.controls['password'].setValue('1234');
//     expect(component.loginForm.controls['password'].valid).toBeFalsy();
//   });
//
//   it('should call dataService.sendLoginDetails() on form submit', () => {
//     spyOn(dataService, 'sendLoginDetails').and.returnValue(of({success: true, firstName: 'John', lastName: 'Doe', encodedAccess: 'Q1VTVE9NX0NPTlNUVVBFUg==', uniqueSessionId: '123'}));
//     component.loginForm.controls['userName'].setValue('test');
//     component.loginForm.controls['password'].setValue('password123');
//     component.onSubmit();
//     expect(dataService.sendLoginDetails).toHaveBeenCalled();
//   });
//
//   it('should show alert for unsuccessful login', () => {
//     spyOn(dataService, 'sendLoginDetails').and.returnValue(of({success: false, message: 'Invalid credentials'}));
//     spyOn(window, 'alert');
//     component.loginForm.controls['userName'].setValue('test');
//     component.loginForm.controls['password'].setValue('password123');
//     component.onSubmit();
//     expect(window.alert).toHaveBeenCalledWith('Invalid credentials');
//   });
//
//   it('should set session values on successful login and navigate to user-account for CUSTOMER', () => {
//     spyOn(dataService, 'sendLoginDetails').and.returnValue(of({success: true, firstName: 'John', lastName: 'Doe', encodedAccess: 'Q1VTVE9NX0NPTlNUVVBFUg==', uniqueSessionId: '123'}));
//     spyOn(localStorage, 'setItem');
//     spyOn(dataService, 'setSessionValues');
//     spyOn(dataService, 'setFirstName');
//     spyOn(dataService, 'setLastName
