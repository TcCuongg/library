import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageTurnoverComponent } from './manage-turnover.component';

describe('ManageTurnoverComponent', () => {
  let component: ManageTurnoverComponent;
  let fixture: ComponentFixture<ManageTurnoverComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageTurnoverComponent]
    });
    fixture = TestBed.createComponent(ManageTurnoverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
