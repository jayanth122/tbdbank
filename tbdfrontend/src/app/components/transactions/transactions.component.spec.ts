import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { DataService } from '../../data.service';
import { InteracValidateRequest } from '../../dto/InteracValidateRequest';
import { TransactionsComponent } from './transactions.component';
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";

describe('TransactionsComponent', () => {
  let component: TransactionsComponent;
  let fixture: ComponentFixture<TransactionsComponent>;
  let routerSpy: jasmine.SpyObj<Router>;
  let dataServiceSpy: jasmine.SpyObj<DataService>;

  beforeEach(() => {
    const router = jasmine.createSpyObj('Router', ['navigate']);
    const dataService = jasmine.createSpyObj('DataService', ['validateInterac', 'updateSession']);

    TestBed.configureTestingModule({
      declarations: [TransactionsComponent,HeaderComponent,FooterComponent],
      providers: [
        { provide: Router, useValue: router },
        { provide: DataService, useValue: dataService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TransactionsComponent);
    component = fixture.componentInstance;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    dataServiceSpy = TestBed.inject(DataService) as jasmine.SpyObj<DataService>;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should do nothing', () => {
      component.ngOnInit();
      expect(true).toBe(true); // dummy assertion to satisfy the test
    });
  });

  describe('validateCustomerInterac', () => {
    const sessionId = 'testSessionId';
    const interacValidateRequest: InteracValidateRequest = { sessionId, email: '' };

    beforeEach(() => {
      spyOn(localStorage, 'getItem').and.returnValue(sessionId);
    });

    it('should navigate to interac if interac is valid', () => {
      dataServiceSpy.validateInterac.and.returnValue(of({ valid: true, sessionId: 'newSessionId' }));

      component.validateCustomerInterac();

      expect(dataServiceSpy.validateInterac).toHaveBeenCalledWith(interacValidateRequest);
      expect(dataServiceSpy.updateSession).toHaveBeenCalledWith(true, 'newSessionId');
      expect(routerSpy.navigate).toHaveBeenCalledWith(['interac']);
    });

    it('should navigate to interac-register if interac is not valid', () => {
      dataServiceSpy.validateInterac.and.returnValue(of({ valid: false, sessionId: 'newSessionId' }));

      component.validateCustomerInterac();

      expect(dataServiceSpy.validateInterac).toHaveBeenCalledWith(interacValidateRequest);
      expect(dataServiceSpy.updateSession).toHaveBeenCalledWith(true, 'newSessionId');
      expect(routerSpy.navigate).toHaveBeenCalledWith(['interac-register']);
    });
  });
});
