// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { HttpClientModule } from '@angular/common/http';
// import { HeaderComponent } from '../header/header.component';
// import { FooterComponent } from '../footer/footer.component';
// import { RouterModule } from '@angular/router';
// import { ReactiveFormsModule } from '@angular/forms'; // add this import statement
//
// import { LoginComponent } from './login.component';
//
// describe('LoginComponent', () => {
//   let component: LoginComponent;
//   let fixture: ComponentFixture<LoginComponent>;
//
//   beforeEach(async () => {
//     await TestBed.configureTestingModule({
//       declarations: [ LoginComponent, HeaderComponent, FooterComponent ],
//             imports: [ HttpClientModule, RouterModule, ReactiveFormsModule ],
//
//     })
//     .compileComponents();
//
//     fixture = TestBed.createComponent(LoginComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });
//
//   it('should create', () => {
//     expect(component).toBeTruthy();
//   });
// });
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { LoginComponent } from './login.component';
import {of} from "rxjs";


describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent, HeaderComponent, FooterComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        ReactiveFormsModule
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the login component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the login form', () => {
    expect(component.loginForm).toBeDefined();
    expect(component.loginForm.controls['userName'].value).toEqual('');
    expect(component.loginForm.controls['password'].value).toEqual('');
  });

  it('should validate the login form', () => {
    expect(component.loginForm.valid).toBeFalsy();
    component.loginForm.controls['userName'].setValue('john.doe');
    component.loginForm.controls['password'].setValue('password');
    expect(component.loginForm.valid).toBeTruthy();
  });
  //
  // it('should submit the login form', () => {
  //   spyOn(component.dataService, 'sendLoginDetails').and.callFake(() => {
  //     return of({
  //       subscribe: (callback: any) => {
  //         callback({
  //           success: true,
  //           firstName: 'John',
  //           lastName: 'Doe',
  //           uniqueSessionId: '123456',
  //           encodedAccess: 'Q1VTVE9N',
  //           message: ''
  //         });
  //       }
  //     });
  //   });
  //   component.loginForm.controls['userName'].setValue('john.doe');
  //   component.loginForm.controls['password'].setValue('password');
  //   localStorage.setItem('userName', 'john.doe')
  //   component.onSubmit();
  //   expect(component.submitted).toBeTruthy();
  //   expect(component.dataService.sendLoginDetails).toHaveBeenCalled();
  //   expect(localStorage.getItem('userName')).toEqual('john.doe');
  //   expect(component.dataService.isLoginValid).toBe(true);
  //   expect(component.router.navigate).toHaveBeenCalledWith(['/user-account']);
  //
    it('should navigate to registration page', () => {
      spyOn(component.router, 'navigate');
      component.goToReg();
      expect(component.router.navigate).toHaveBeenCalledWith(['registration']);
    });
  // });
})
