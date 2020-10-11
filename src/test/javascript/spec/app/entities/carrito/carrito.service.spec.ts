import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CarritoService } from 'app/entities/carrito/carrito.service';
import { ICarrito, Carrito } from 'app/shared/model/carrito.model';

describe('Service Tests', () => {
  describe('Carrito Service', () => {
    let injector: TestBed;
    let service: CarritoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICarrito;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CarritoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Carrito(
        0,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        false,
        0,
        0,
        currentDate,
        'AAAAAAA',
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaPedido: currentDate.format(DATE_TIME_FORMAT),
            fechaPago: currentDate.format(DATE_TIME_FORMAT),
            fechaConfirmadoPago: currentDate.format(DATE_TIME_FORMAT),
            fechaEntrega: currentDate.format(DATE_TIME_FORMAT),
            fechaTermino: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Carrito', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaPedido: currentDate.format(DATE_TIME_FORMAT),
            fechaPago: currentDate.format(DATE_TIME_FORMAT),
            fechaConfirmadoPago: currentDate.format(DATE_TIME_FORMAT),
            fechaEntrega: currentDate.format(DATE_TIME_FORMAT),
            fechaTermino: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaPedido: currentDate,
            fechaPago: currentDate,
            fechaConfirmadoPago: currentDate,
            fechaEntrega: currentDate,
            fechaTermino: currentDate
          },
          returnedFromService
        );
        service
          .create(new Carrito(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Carrito', () => {
        const returnedFromService = Object.assign(
          {
            fechaPedido: currentDate.format(DATE_TIME_FORMAT),
            fechaPago: currentDate.format(DATE_TIME_FORMAT),
            fechaConfirmadoPago: currentDate.format(DATE_TIME_FORMAT),
            fechaEntrega: currentDate.format(DATE_TIME_FORMAT),
            estado: 'BBBBBB',
            telefonoContacto: 'BBBBBB',
            whatsapp: true,
            puntajeComprador: 1,
            puntajeVendedor: 1,
            fechaTermino: currentDate.format(DATE_TIME_FORMAT),
            razonTermino: 'BBBBBB',
            montoTotalCompra: 1,
            montoDelivery: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaPedido: currentDate,
            fechaPago: currentDate,
            fechaConfirmadoPago: currentDate,
            fechaEntrega: currentDate,
            fechaTermino: currentDate
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

      it('should return a list of Carrito', () => {
        const returnedFromService = Object.assign(
          {
            fechaPedido: currentDate.format(DATE_TIME_FORMAT),
            fechaPago: currentDate.format(DATE_TIME_FORMAT),
            fechaConfirmadoPago: currentDate.format(DATE_TIME_FORMAT),
            fechaEntrega: currentDate.format(DATE_TIME_FORMAT),
            estado: 'BBBBBB',
            telefonoContacto: 'BBBBBB',
            whatsapp: true,
            puntajeComprador: 1,
            puntajeVendedor: 1,
            fechaTermino: currentDate.format(DATE_TIME_FORMAT),
            razonTermino: 'BBBBBB',
            montoTotalCompra: 1,
            montoDelivery: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaPedido: currentDate,
            fechaPago: currentDate,
            fechaConfirmadoPago: currentDate,
            fechaEntrega: currentDate,
            fechaTermino: currentDate
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

      it('should delete a Carrito', () => {
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
