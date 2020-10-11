import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NexoTestModule } from '../../../test.module';
import { ProductoImagenesComponent } from 'app/entities/producto-imagenes/producto-imagenes.component';
import { ProductoImagenesService } from 'app/entities/producto-imagenes/producto-imagenes.service';
import { ProductoImagenes } from 'app/shared/model/producto-imagenes.model';

describe('Component Tests', () => {
  describe('ProductoImagenes Management Component', () => {
    let comp: ProductoImagenesComponent;
    let fixture: ComponentFixture<ProductoImagenesComponent>;
    let service: ProductoImagenesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoImagenesComponent],
        providers: []
      })
        .overrideTemplate(ProductoImagenesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoImagenesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoImagenesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductoImagenes(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productoImagenes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
