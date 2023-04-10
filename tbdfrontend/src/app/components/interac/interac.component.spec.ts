/*import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import {of, Subject} from 'rxjs';

import { InteracComponent } from './interac.component';
import { DataService } from '../../data.service';
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";

describe('InteracComponent', () => {
  let component: InteracComponent;
  let fixture: ComponentFixture<InteracComponent>;
  let dataService: DataService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ReactiveFormsModule, RouterTestingModule],
      declarations: [InteracComponent,HeaderComponent,FooterComponent],
      providers: [DataService]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InteracComponent);
    component = fixture.componentInstance;
    dataService = TestBed.inject(DataService);
    fixture.detectChanges();
  });

  afterEach(() => {
    fixture.destroy();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /*
  it('should disable the amount field initially', () => {
    expect(component.interacForm.get('amount')?.disabled).toBeTrue();
  });*/
/*
  it('should disable the security question and answer fields initially', () => {
    expect(component.interacForm.get('securityQuestion')?.disabled).toBeTrue();
    expect(component.interacForm.get('securityAnswer')?.disabled).toBeTrue();
  });
*/
  // it('should call the validateInterac method on change of email input', () => {
  //   fixture.detectChanges(); // detect changes to render the component
  //   const emailInput = fixture.nativeElement.querySelector('input[name="receiverEmail"]');
  //   expect(emailInput).toBeTruthy(); // check if email input exists
  //   emailInput.value = 'test@example.com';
  //   emailInput.dispatchEvent(new Event('input'));
  //   const validateInteracSpy = spyOn(component['dataService'], 'validateInterac').and.returnValue(of({}));
  //   fixture.detectChanges(); // detect changes after input event
  //   expect(validateInteracSpy).toHaveBeenCalled();
  // });
/*
  it('should disable the amount field when the email is not linked to a bank account', () => {
    const validateInteracSubject = new Subject();
    validateInteracSpy.and.returnValue(validateInteracSubject);
    component.interacForm.patchValue({ receiverEmail: 'test@example.com' });
    validateInteracSubject.next({ valid: false });
    expect(component.amountEnabled).toBeFalse();
    expect(component.interacForm.get('amount')?.disabled).toBeTrue();
  });*/
/*
  it('should update the session when a valid Interac form is submitted', () => {
    const sendInteracDetailsSubject = new Subject();
    spyOn(dataService, 'sendInteracDetails').and.returnValue(sendInteracDetailsSubject);
    component.interacForm.patchValue({
      receiverEmail: 'test@example.com',
      amount: '50.00',
      message: 'Test message',
      securityQuestion: 'Test question',
      securityAnswer: 'Test answer'
    });
  });*/
//})
