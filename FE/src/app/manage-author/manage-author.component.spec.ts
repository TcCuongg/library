import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAuthorComponent } from './manage-author.component';

describe('ManageAuthorComponent', () => {
  let component: ManageAuthorComponent;
  let fixture: ComponentFixture<ManageAuthorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageAuthorComponent]
    });
    fixture = TestBed.createComponent(ManageAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
