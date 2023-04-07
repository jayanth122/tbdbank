import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';
import { ReactiveFormsModule } from '@angular/forms';

import { InteracRegisterComponent } from './interac-register.component';

describe('InteracRegisterComponent', () => {
  let component: InteracRegisterComponent;
  let fixture: ComponentFixture<InteracRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InteracRegisterComponent, HeaderComponent ],
       imports: [ HttpClientModule,ReactiveFormsModule],


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
