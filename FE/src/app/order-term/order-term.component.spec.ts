import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderTermComponent } from './order-term.component';

describe('OrderTermComponent', () => {
  let component: OrderTermComponent;
  let fixture: ComponentFixture<OrderTermComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderTermComponent]
    });
    fixture = TestBed.createComponent(OrderTermComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
