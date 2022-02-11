import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnlyFilesComponent } from './only-files.component';

describe('OnlyFilesComponent', () => {
  let component: OnlyFilesComponent;
  let fixture: ComponentFixture<OnlyFilesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OnlyFilesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OnlyFilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
