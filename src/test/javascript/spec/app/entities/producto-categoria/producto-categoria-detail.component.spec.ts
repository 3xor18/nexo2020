import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { ProductoCategoriaDetailComponent } from 'app/entities/producto-categoria/producto-categoria-detail.component';
import { ProductoCategoria } from 'app/shared/model/producto-categoria.model';

describe('Component Tests', () => {
  describe('ProductoCategoria Management Detail Component', () => {
    let comp: ProductoCategoriaDetailComponent;
    let fixture: ComponentFixture<ProductoCategoriaDetailComponent>;
    const route = ({ data: of({ productoCategoria: new ProductoCategoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoCategoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductoCategoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductoCategoriaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productoCategoria).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
