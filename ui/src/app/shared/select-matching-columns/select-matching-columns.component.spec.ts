import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectMatchingColumnsComponent } from './select-matching-columns.component';

describe('SelectMatchingColumnsComponent', () => {
  let component: SelectMatchingColumnsComponent;
  let fixture: ComponentFixture<SelectMatchingColumnsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectMatchingColumnsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectMatchingColumnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
