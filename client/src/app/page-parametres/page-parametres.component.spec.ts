import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageParametresComponent } from './page-parametres.component';

describe('PageParametresComponent', () => {
  let component: PageParametresComponent;
  let fixture: ComponentFixture<PageParametresComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PageParametresComponent]
    });
    fixture = TestBed.createComponent(PageParametresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
