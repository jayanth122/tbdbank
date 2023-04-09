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

describe('StatementComponent', () => {
  let component: StatementComponent;
  let fixture: ComponentFixture<StatementComponent>;
  let dataServiceSpy: jasmine.SpyObj<DataService>;
  let routerSpy: jasmine.SpyObj<Router>;
  let formBuilder: FormBuilder;
  let dataService: DataService;

  beforeEach(async () => {
    dataServiceSpy = jasmine.createSpyObj('DataService', ['getTransactionStatement', 'setStatementPdf', 'updateSession', 'isLoginValid']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    await TestBed.configureTestingModule({
      declarations: [ StatementComponent, HeaderComponent ],
      imports: [ HttpClientModule, ReactiveFormsModule ],
      providers: [
        { provide: DataService, useValue: dataServiceSpy },
        { provide: Router, useValue: routerSpy },
        FormBuilder,DataService
      ],

    })
    .compileComponents();

    fixture = TestBed.createComponent(StatementComponent);
    component = fixture.componentInstance;
    formBuilder = TestBed.inject(FormBuilder);
    fixture.detectChanges();
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

});
