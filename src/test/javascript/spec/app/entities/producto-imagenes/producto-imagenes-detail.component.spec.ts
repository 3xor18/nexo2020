import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { ProductoImagenesDetailComponent } from 'app/entities/producto-imagenes/producto-imagenes-detail.component';
import { ProductoImagenes } from 'app/shared/model/producto-imagenes.model';

describe('Component Tests', () => {
  describe('ProductoImagenes Management Detail Component', () => {
    let comp: ProductoImagenesDetailComponent;
    let fixture: ComponentFixture<ProductoImagenesDetailComponent>;
    const route = ({ data: of({ productoImagenes: new ProductoImagenes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoImagenesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductoImagenesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductoImagenesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productoImagenes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
