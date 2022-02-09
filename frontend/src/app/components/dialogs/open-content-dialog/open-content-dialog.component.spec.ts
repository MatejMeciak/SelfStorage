import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenContentDialogComponent } from './open-content-dialog.component';

describe('OpenContentDialogComponent', () => {
  let component: OpenContentDialogComponent;
  let fixture: ComponentFixture<OpenContentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OpenContentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenContentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
