import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageMessComponent } from './manage-mess.component';

describe('ManageMessComponent', () => {
  let component: ManageMessComponent;
  let fixture: ComponentFixture<ManageMessComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageMessComponent]
    });
    fixture = TestBed.createComponent(ManageMessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
