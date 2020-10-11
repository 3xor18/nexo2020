import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProductoImagenesService } from 'app/entities/producto-imagenes/producto-imagenes.service';
import { IProductoImagenes, ProductoImagenes } from 'app/shared/model/producto-imagenes.model';

describe('Service Tests', () => {
  describe('ProductoImagenes Service', () => {
    let injector: TestBed;
    let service: ProductoImagenesService;
    let httpMock: HttpTestingController;
    let elemDefault: IProductoImagenes;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProductoImagenesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ProductoImagenes(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a ProductoImagenes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaBd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaBd: currentDate
          },
          returnedFromService
        );
        service
          .create(new ProductoImagenes(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ProductoImagenes', () => {
        const returnedFromService = Object.assign(
          {
            estado: 'BBBBBB',
            fechaBd: currentDate.format(DATE_TIME_FORMAT),
            path: 'BBBBBB',
            nombre: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should return a list of ProductoImagenes', () => {
        const returnedFromService = Object.assign(
          {
            estado: 'BBBBBB',
            fechaBd: currentDate.format(DATE_TIME_FORMAT),
            path: 'BBBBBB',
            nombre: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
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

      it('should delete a ProductoImagenes', () => {
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
