import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { CarritoDetailComponent } from 'app/entities/carrito/carrito-detail.component';
import { Carrito } from 'app/shared/model/carrito.model';

describe('Component Tests', () => {
  describe('Carrito Management Detail Component', () => {
    let comp: CarritoDetailComponent;
    let fixture: ComponentFixture<CarritoDetailComponent>;
    const route = ({ data: of({ carrito: new Carrito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CarritoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarritoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carrito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
