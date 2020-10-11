import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { ComunaDetailComponent } from 'app/entities/comuna/comuna-detail.component';
import { Comuna } from 'app/shared/model/comuna.model';

describe('Component Tests', () => {
  describe('Comuna Management Detail Component', () => {
    let comp: ComunaDetailComponent;
    let fixture: ComponentFixture<ComunaDetailComponent>;
    const route = ({ data: of({ comuna: new Comuna(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ComunaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComunaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComunaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comuna).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
