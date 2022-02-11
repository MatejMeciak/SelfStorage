import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnlyFoldersComponent } from './only-folders.component';

describe('OnlyFoldersComponent', () => {
  let component: OnlyFoldersComponent;
  let fixture: ComponentFixture<OnlyFoldersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OnlyFoldersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OnlyFoldersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
