import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InteracComponent } from './interac.component';

describe('InteracComponent', () => {
  let component: InteracComponent;
  let fixture: ComponentFixture<InteracComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InteracComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InteracComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
