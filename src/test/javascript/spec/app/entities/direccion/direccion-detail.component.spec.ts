import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { DireccionDetailComponent } from 'app/entities/direccion/direccion-detail.component';
import { Direccion } from 'app/shared/model/direccion.model';

describe('Component Tests', () => {
  describe('Direccion Management Detail Component', () => {
    let comp: DireccionDetailComponent;
    let fixture: ComponentFixture<DireccionDetailComponent>;
    const route = ({ data: of({ direccion: new Direccion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [DireccionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DireccionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DireccionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.direccion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
