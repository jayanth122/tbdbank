// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { LoginComponent } from './login.component';
// import { RouterTestingModule } from '@angular/router/testing';
// import { DataService } from '../../data.service';
// import { FormBuilder } from '@angular/forms';
// import { HttpClientTestingModule } from '@angular/common/http/testing';
//
// describe('LoginComponent', () => {
//   let component: LoginComponent;
//   let fixture: ComponentFixture<LoginComponent>;
//
//   beforeEach(async () => {
//     await TestBed.configureTestingModule({
//       imports: [ RouterTestingModule, HttpClientTestingModule ],
//       declarations: [ LoginComponent ],
//       providers: [ DataService, FormBuilder ]
//     })
//       .compileComponents();
//   });
//
//   beforeEach(() => {
//     fixture = TestBed.createComponent(LoginComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });
//
//   it('should create the component', () => {
//     expect(component).toBeTruthy();
//   });
//
//   it('should initialize the login form with empty fields', () => {
//     expect(component.loginForm.value.userName).toBe('');
//     expect(component.loginForm.value.password).toBe('');
//   });
//
//   it('should mark the form as invalid if any field is empty', () => {
//     component.loginForm.controls['userName'].setValue('');
//     component.loginForm.controls['password'].setValue('password');
//     expect(component.loginForm.valid).toBeFalsy();
//
//     component.loginForm.controls['userName'].setValue('user');
//     component.loginForm.controls['password'].setValue('');
//     expect(component.loginForm.valid).toBeFalsy();
//
//     component.loginForm.controls['userName'].setValue('');
//     component.loginForm.controls['password'].setValue('');
//     expect(component.loginForm.valid).toBeFalsy();
//   });
//
//   it('should mark the form as invalid if password is less than 8 characters', () => {
//     component.loginForm.controls['userName'].setValue('customer_san');
//     component.loginForm.controls['password'].setValue('encrypted_password');
//     expect(component.loginForm.valid).toBeFalsy();
//   });
//
//   it('should mark the form as valid if all fields are filled out and password is at least 8 characters', () => {
//     component.loginForm.controls['userName'].setValue('user');
//     component.loginForm.controls['password'].setValue('password');
//     expect(component.loginForm.valid).toBeTruthy();
//   });
//
//   it('should call the onSubmit method when the login form is submitted', () => {
//     spyOn(component, 'onSubmit');
//     const button = fixture.debugElement.nativeElement.querySelector('button');
//     button.click();
//     expect(component.onSubmit).toHaveBeenCalled();
//   });
//
// });
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { RouterTestingModule } from '@angular/router/testing';
import { DataService } from '../../data.service';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let dataServiceSpy: jasmine.SpyObj<DataService>;

  beforeEach(waitForAsync(() => {
    dataServiceSpy = jasmine.createSpyObj<DataService>(['sendLoginDetails', 'setSessionValues', 'setIsLoginValid']);

    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [
        RouterTestingModule,
        ReactiveFormsModule,
        HttpClientTestingModule
      ],
      providers: [
        FormBuilder,
        { provide: DataService, useValue: dataServiceSpy }
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set submitted to true on submit', () => {
    component.onSubmit();
    expect(component.submitted).toBe(true);
  });

  it('should call sendLoginDetails() with the correct arguments when form is valid and submitted', () => {
    // set the form values to a valid username and password
    component.loginForm.setValue({
      userName: 'validusername',
      password: 'validpassword123'
    });
    // create a new FormData object and append the form values to it
    const formData = new FormData();
    formData.append('userName', component.loginForm.value.userName);
    formData.append('password', component.loginForm.value.password);
    // create a spy on the DataService sendLoginDetails() function
    const sendLoginDetailsSpy = spyOn(dataServiceSpy, 'sendLoginDetails').and.returnValue(of({ success: true }));
    // call the component onSubmit() function to simulate form submission
    component.onSubmit();
    // check that the sendLoginDetails() function was called with the correct arguments
    expect(sendLoginDetailsSpy).toHaveBeenCalledWith(formData);
  });


  it('should set session values on successful login', () => {
    const loginDetails = { userName: 'testuser', password: 'testpassword' };
    const sessionValues = { userName: 'testuser', uniqueSessionId: 'testsessionid' };
    const encodedAccess = 'Q1VTVE9SUkU='; // 'CUSTOMER' in base64
    const loginResponse = { success: true, firstName: 'Test', lastName: 'User', uniqueSessionId: 'testsessionid', encodedAccess: encodedAccess };
    component.loginForm.setValue(loginDetails);
    dataServiceSpy.sendLoginDetails.and.returnValue(of(loginResponse));
    component.onSubmit();
    expect(dataServiceSpy.setSessionValues).toHaveBeenCalledWith(sessionValues.userName, sessionValues.uniqueSessionId);
    expect(dataServiceSpy.setIsLoginValid).toHaveBeenCalledWith(true, loginResponse.uniqueSessionId);
  });

  it('should navigate to user account page on successful customer login', () => {
    const loginDetails = { userName: 'testuser', password: 'testpassword' };
    const encodedAccess = 'Q1VTVE9SUkU='; // 'CUSTOMER' in base64
    const loginResponse = { success: true, firstName: 'Test', lastName: 'User', uniqueSessionId: 'testsessionid', encodedAccess: encodedAccess };
    component.loginForm.setValue(loginDetails);
    dataServiceSpy.sendLoginDetails.and.returnValue(of(loginResponse));
    spyOn(component.router, 'navigate');
    component.onSubmit();
    expect(component.router.navigate).toHaveBeenCalledWith(['user-account']);
  });

  it('should navigate to manager dashboard on successful manager login', () => {
    // TODO: Implement this test
  });

});

