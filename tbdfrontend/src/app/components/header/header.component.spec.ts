import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HeaderComponent } from './header.component';
import { DataService } from '../../data.service';
import { HttpClientModule } from '@angular/common/http';
import { of } from 'rxjs';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let dataService: DataService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderComponent ],
      imports: [ RouterTestingModule, HttpClientModule ],
      providers: [ DataService ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    dataService = TestBed.inject(DataService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('logOut', () => {
    // it('should navigate to login page if user is not logged in', () => {
    //   spyOn(component.router, 'navigate');
    //   spyOn(localStorage, 'getItem').and.returnValue('test-session-id');
    //   spyOnProperty(dataService, 'isLoginValid', 'get').and.returnValue(false);
    //   component.logOut();
    //
    //   expect(component.router.navigate).toHaveBeenCalledWith(['login']);
    // });

    it('should log out and navigate to login page if user is logged in', () => {
      spyOn(component.router, 'navigate');
      spyOn(localStorage, 'getItem').and.returnValue('test-session-id');
      spyOn(dataService, 'logOut').and.returnValue(of('success'));
      spyOn(dataService, 'updateSession');
      spyOn(localStorage, 'clear');

      component.logOut();

      expect(dataService.logOut).toHaveBeenCalledWith(jasmine.objectContaining({
        sessionId: 'test-session-id'
      }));

      expect(localStorage.clear).toHaveBeenCalled();
      expect(dataService.updateSession).toHaveBeenCalledWith(false, '');
      expect(component.router.navigate).toHaveBeenCalledWith(['login']);
      expect(localStorage.clear).toHaveBeenCalledOnceWith();
      expect(dataService.updateSession).toHaveBeenCalledOnceWith(false, '');
    });
  });
});

