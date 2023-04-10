import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HeaderComponent } from '../header/header.component';
import { ReactiveFormsModule } from '@angular/forms'; // add this import statement
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';
import { saveAs } from 'file-saver';
import { StatementComponent } from './statement.component';
import { DataService } from '../../data.service';
import { RouterTestingModule } from '@angular/router/testing';
import { Transaction} from "../../dto/Transaction";
import {StatementRequest} from "../../dto/StatementRequest";
import { DatePipe } from '@angular/common';

import { tick, fakeAsync } from '@angular/core/testing';
import Expected = jasmine.Expected;

describe('StatementComponent', () => {
  let component: StatementComponent;
  let fixture: ComponentFixture<StatementComponent>;
  let dataServiceSpy: jasmine.SpyObj<DataService>;
  let routerSpy: jasmine.SpyObj<Router>;
  let formBuilder: FormBuilder;
  let dataService: DataService;
  let router: Router;
  let saveAsSpy: jasmine.Spy;

  beforeEach(async () => {
    dataServiceSpy = jasmine.createSpyObj('DataService', ['getTransactionStatement', 'setStatementPdf', 'updateSession', 'isLoginValid']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    formBuilder = new FormBuilder();
    saveAsSpy = spyOn(saveAs, 'saveAs').and.stub();
    dataService = jasmine.createSpyObj('DataService', ['getTransactionStatement', 'setStatementPdf', 'updateSession', 'isLoginValid','transactionList']);
    await TestBed.configureTestingModule({
      declarations: [ StatementComponent, HeaderComponent ],
      imports: [ HttpClientModule, ReactiveFormsModule, RouterTestingModule ],
      providers: [
        { provide: DataService, useValue: dataServiceSpy },
        { provide: Router, useValue: routerSpy },{ provide: FormBuilder, useValue: formBuilder },
        FormBuilder,DataService
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(StatementComponent);
    component = fixture.componentInstance;
    formBuilder = TestBed.inject(FormBuilder);
    fixture.detectChanges();
    router = TestBed.inject(Router);
    dataService = TestBed.inject(DataService);
  });
  afterEach(() => {
    localStorage.removeItem('sessionId');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to login if not logged in', () => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['isLoginValid']);
    dataService.isLoginValid.and.returnValue(false);
    component.ngOnInit();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['login']);
  });
  it('should navigate to login if not logged in', () => {
    const dataService = jasmine.createSpyObj('DataService', ['isLoginValid']);
    spyOn(localStorage, 'getItem').and.returnValue(null);
    dataService.isLoginValid.and.returnValue(false);

    component.onSubmit();

    expect(router.navigate).toHaveBeenCalledWith(['login']);
  });
  it('should download statement PDF', () => {
    const mockStatementPdf = 'mockStatementPdf';
    spyOn(component, 'onSubmit').and.callFake(() => {
      component.transactions = [];
      dataService.setStatementPdf(mockStatementPdf);
      return of({ success: true, sessionId: '123', statementPdf: mockStatementPdf, transactionList: [] });
    });
    spyOn(dataService, 'getStatementPdf').and.returnValue(mockStatementPdf);

    component.statementForm.setValue({
      fromDate: '2022-01-01',
      toDate: '2022-01-02'
    });

    component.onSubmit();
    component.downloadPdf();

    expect(saveAsSpy).toHaveBeenCalledWith(jasmine.any(Blob), 'your_statement_filename.pdf');
  });
  /*it('should set transactions property after successful API call', () => {
    const data = {
      success: true,
      sessionId: 'session-id',
      statementPdf: 'statement-pdf',
      transactionList: [
        {
          transactionId: '123',
          customerId: '456',
          amount: 100,
          date: '2022-04-01',
          roundedAmount: 100,
          balance: 500,
          transactionType: 'debit',
          description: 'Test transaction'
        },
        {
          transactionId: '456',
          customerId: '789',
          amount: 50,
          date: '2022-04-02',
          roundedAmount: 50,
          balance: 450,
          transactionType: 'credit',
          description: 'Another test transaction'
        }
      ]
    };
    const dataServiceSpy = TestBed.inject(DataService) as jasmine.SpyObj<DataService>;
    dataServiceSpy.getTransactionStatement.and.returnValue(of(data));

    component.onSubmit();

    expect(component.transactions).toEqual(data.transactionList);
  });

*/

});
