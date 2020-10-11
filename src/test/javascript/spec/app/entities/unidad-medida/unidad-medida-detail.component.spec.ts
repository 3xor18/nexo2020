import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { UnidadMedidaDetailComponent } from 'app/entities/unidad-medida/unidad-medida-detail.component';
import { UnidadMedida } from 'app/shared/model/unidad-medida.model';

describe('Component Tests', () => {
  describe('UnidadMedida Management Detail Component', () => {
    let comp: UnidadMedidaDetailComponent;
    let fixture: ComponentFixture<UnidadMedidaDetailComponent>;
    const route = ({ data: of({ unidadMedida: new UnidadMedida(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [UnidadMedidaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UnidadMedidaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnidadMedidaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unidadMedida).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
