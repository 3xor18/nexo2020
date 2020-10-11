import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { ProductoImpuestosDetailComponent } from 'app/entities/producto-impuestos/producto-impuestos-detail.component';
import { ProductoImpuestos } from 'app/shared/model/producto-impuestos.model';

describe('Component Tests', () => {
  describe('ProductoImpuestos Management Detail Component', () => {
    let comp: ProductoImpuestosDetailComponent;
    let fixture: ComponentFixture<ProductoImpuestosDetailComponent>;
    const route = ({ data: of({ productoImpuestos: new ProductoImpuestos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoImpuestosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductoImpuestosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductoImpuestosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productoImpuestos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
