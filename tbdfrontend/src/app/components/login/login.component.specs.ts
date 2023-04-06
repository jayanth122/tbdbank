import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { RouterTestingModule } from '@angular/router/testing';
import { DataService } from '../../data.service';
import { FormBuilder } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ RouterTestingModule, HttpClientTestingModule ],
      declarations: [ LoginComponent ],
      providers: [ DataService, FormBuilder ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the login form with empty fields', () => {
    expect(component.loginForm.value.userName).toBe('');
    expect(component.loginForm.value.password).toBe('');
  });

  it('should mark the form as invalid if any field is empty', () => {
    component.loginForm.controls['userName'].setValue('');
    component.loginForm.controls['password'].setValue('password');
    expect(component.loginForm.valid).toBeFalsy();

    component.loginForm.controls['userName'].setValue('user');
    component.loginForm.controls['password'].setValue('');
    expect(component.loginForm.valid).toBeFalsy();

    component.loginForm.controls['userName'].setValue('');
    component.loginForm.controls['password'].setValue('');
    expect(component.loginForm.valid).toBeFalsy();
  });

  it('should mark the form as invalid if password is less than 8 characters', () => {
    component.loginForm.controls['userName'].setValue('customer_san');
    component.loginForm.controls['password'].setValue('encrypted_password');
    expect(component.loginForm.valid).toBeFalsy();
  });

  it('should mark the form as valid if all fields are filled out and password is at least 8 characters', () => {
    component.loginForm.controls['userName'].setValue('user');
    component.loginForm.controls['password'].setValue('password');
    expect(component.loginForm.valid).toBeTruthy();
  });

  it('should call the onSubmit method when the login form is submitted', () => {
    spyOn(component, 'onSubmit');
    const button = fixture.debugElement.nativeElement.querySelector('button');
    button.click();
    expect(component.onSubmit).toHaveBeenCalled();
  });

});
