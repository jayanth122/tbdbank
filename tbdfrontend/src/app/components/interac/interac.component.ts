import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { DataService } from '../../data.service';
import { InteracValidateRequest } from '../../dto/InteracValidateRequest';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil, debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-interac',
  templateUrl: './interac.component.html',
  styleUrls: ['./interac.component.scss']
})
export class InteracComponent implements OnInit, OnDestroy {
  interacForm!: FormGroup;
  interacFirstName!: string;
  interacLastName!: string;
  interacBankName!: string;
  interacEmailError!: string;
  notLinked = false;
  amountEnabled = false;
  submitted = false;
  private readonly destroy$: Subject<void> = new Subject();

  constructor(
    private formBuilder: FormBuilder,
    private dataService: DataService,
    private http: HttpClient,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.interacEmailError = '';
    this.interacForm = this.formBuilder.group({
      receiverEmail: ['', [Validators.required, Validators.email]],
      amount: ['', Validators.required],
      message: [''],
      securityQuestion: ['', Validators.required],
      securityAnswer: ['', Validators.required]
    });
    this.interacForm.get('amount')?.disable();

    this.interacForm
      .get('receiverEmail')
      ?.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((email) => {
        if (this.validateEmail(email)) {
          const interacValidateRequest: InteracValidateRequest = {
            sessionId: localStorage.getItem('sessionId') as string,
            email: email
          };
          return this.dataService.validateInterac(interacValidateRequest);
        } else {
          this.interacForm.get('amount')?.disable();
          this.amountEnabled = false;
          return [];
        }
      }),
      takeUntil(this.destroy$)
    )
      .subscribe((data) => {
        if (data.valid) {
          this.amountEnabled = true;
          this.interacForm.get('amount')?.enable();
          this.interacFirstName = data.firstName;
          this.interacLastName = data.lastName;
          this.interacBankName = data.bankName;
          this.dataService.updateSession(true, data.sessionId);
        } else {
          this.notLinked = true;
          this.interacEmailError = 'Email not linked to any bank account';
          this.amountEnabled = false;
          this.dataService.updateSession(true, data.sessionId);
          this.interacForm.get('amount')?.disable();
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  onSubmit() {
    if (!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login']);
      return;
    }
    this.submitted = true;
    if (this.interacForm.invalid) {
      return;
    }
    this.interacForm.value['sessionId'] = localStorage.getItem('sessionId') as string;
    this.dataService.sendInteracDetails(this.interacForm.value).subscribe((data) => {
      if (data.success) {
        alert(data.message);
        this.dataService.updateSession(true, data.sessionId);
      }
    });
  }

  validateEmail(email: string): boolean {
    const emailRegex = /^([a-zA-Z0-9._%+-]+)@([a-zA-Z]{5,10})\.([a-zA-Z]{3})$/;
    return emailRegex.test(email);
  }
}








