import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SharedFromPageComponent } from './shared-from-page.component';

describe('SharedPageComponent', () => {
  let component: SharedFromPageComponent;
  let fixture: ComponentFixture<SharedFromPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SharedFromPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SharedFromPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
