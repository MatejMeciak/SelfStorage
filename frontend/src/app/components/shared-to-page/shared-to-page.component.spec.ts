import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SharedToPageComponent } from './shared-to-page.component';

describe('SharedToPageComponent', () => {
  let component: SharedToPageComponent;
  let fixture: ComponentFixture<SharedToPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SharedToPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SharedToPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
