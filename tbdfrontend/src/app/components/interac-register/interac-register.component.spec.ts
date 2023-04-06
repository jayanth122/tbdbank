import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InteracRegisterComponent } from './interac-register.component';

describe('InteracRegisterComponent', () => {
  let component: InteracRegisterComponent;
  let fixture: ComponentFixture<InteracRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InteracRegisterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InteracRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
