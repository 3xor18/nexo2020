import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IProducto, Producto } from 'app/shared/model/producto.model';

describe('Service Tests', () => {
  describe('Producto Service', () => {
    let injector: TestBed;
    let service: ProductoService;
    let httpMock: HttpTestingController;
    let elemDefault: IProducto;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProductoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Producto(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, currentDate, currentDate, 'AAAAAAA', 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaVencimiento: currentDate.format(DATE_FORMAT),
            fechaBd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Producto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaVencimiento: currentDate.format(DATE_FORMAT),
            fechaBd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaVencimiento: currentDate,
            fechaBd: currentDate
          },
          returnedFromService
        );
        service
          .create(new Producto(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Producto', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            codigo: 'BBBBBB',
            codigoBarra: 'BBBBBB',
            descripcion: 'BBBBBB',
            cantidadDisponible: 1,
            alertaMinimo: 1,
            fechaVencimiento: currentDate.format(DATE_FORMAT),
            fechaBd: currentDate.format(DATE_TIME_FORMAT),
            estado: 'BBBBBB',
            precioCompraBruto: 1,
            precioVentaTotalDetal: 1,
            precioVentaTotalMayor: 1,
            unidadMedidaVendida: 1,
            precioAlmayorDespuesde: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaVencimiento: currentDate,
            fechaBd: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Producto', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            codigo: 'BBBBBB',
            codigoBarra: 'BBBBBB',
            descripcion: 'BBBBBB',
            cantidadDisponible: 1,
            alertaMinimo: 1,
            fechaVencimiento: currentDate.format(DATE_FORMAT),
            fechaBd: currentDate.format(DATE_TIME_FORMAT),
            estado: 'BBBBBB',
            precioCompraBruto: 1,
            precioVentaTotalDetal: 1,
            precioVentaTotalMayor: 1,
            unidadMedidaVendida: 1,
            precioAlmayorDespuesde: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaVencimiento: currentDate,
            fechaBd: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Producto', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
