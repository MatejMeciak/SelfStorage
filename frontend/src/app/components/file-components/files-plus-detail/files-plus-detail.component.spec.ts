import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilesPlusDetailComponent } from './files-plus-detail.component';

describe('FilesPlusDetailComponent', () => {
  let component: FilesPlusDetailComponent;
  let fixture: ComponentFixture<FilesPlusDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilesPlusDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilesPlusDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
