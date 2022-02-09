import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputUserDialogComponent } from './input-user-dialog.component';

describe('InputUserDialogComponent', () => {
  let component: InputUserDialogComponent;
  let fixture: ComponentFixture<InputUserDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputUserDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InputUserDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
