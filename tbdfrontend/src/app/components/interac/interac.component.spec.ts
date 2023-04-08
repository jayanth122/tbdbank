import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InteracComponent } from './interac.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

describe('InteracComponent', () => {
  let component: InteracComponent;
  let fixture: ComponentFixture<InteracComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InteracComponent, HeaderComponent, FooterComponent],
      imports: [HttpClientModule, ReactiveFormsModule, RouterModule.forRoot([])],
    }).compileComponents();

    fixture = TestBed.createComponent(InteracComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

