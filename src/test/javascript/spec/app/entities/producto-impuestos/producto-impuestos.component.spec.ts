import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NexoTestModule } from '../../../test.module';
import { ProductoImpuestosComponent } from 'app/entities/producto-impuestos/producto-impuestos.component';
import { ProductoImpuestosService } from 'app/entities/producto-impuestos/producto-impuestos.service';
import { ProductoImpuestos } from 'app/shared/model/producto-impuestos.model';

describe('Component Tests', () => {
  describe('ProductoImpuestos Management Component', () => {
    let comp: ProductoImpuestosComponent;
    let fixture: ComponentFixture<ProductoImpuestosComponent>;
    let service: ProductoImpuestosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoImpuestosComponent],
        providers: []
      })
        .overrideTemplate(ProductoImpuestosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoImpuestosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoImpuestosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductoImpuestos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productoImpuestos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
