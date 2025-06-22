import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCarsComponent } from './show-cars.component';

describe('ShowCarsComponent', () => {
  let component: ShowCarsComponent;
  let fixture: ComponentFixture<ShowCarsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowCarsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowCarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
