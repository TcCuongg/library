import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagePayComponent } from './manage-pay.component';

describe('ManagePayComponent', () => {
  let component: ManagePayComponent;
  let fixture: ComponentFixture<ManagePayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagePayComponent]
    });
    fixture = TestBed.createComponent(ManagePayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
