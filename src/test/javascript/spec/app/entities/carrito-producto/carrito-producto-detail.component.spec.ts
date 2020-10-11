import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { CarritoProductoDetailComponent } from 'app/entities/carrito-producto/carrito-producto-detail.component';
import { CarritoProducto } from 'app/shared/model/carrito-producto.model';

describe('Component Tests', () => {
  describe('CarritoProducto Management Detail Component', () => {
    let comp: CarritoProductoDetailComponent;
    let fixture: ComponentFixture<CarritoProductoDetailComponent>;
    const route = ({ data: of({ carritoProducto: new CarritoProducto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CarritoProductoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarritoProductoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoProductoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carritoProducto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
