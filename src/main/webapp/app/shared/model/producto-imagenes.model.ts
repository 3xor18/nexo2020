import { Moment } from 'moment';

export interface IProductoImagenes {
  id?: number;
  estado?: string;
  fechaBd?: Moment;
  path?: string;
  nombre?: string;
  productoId?: number;
}

export class ProductoImagenes implements IProductoImagenes {
  constructor(
    public id?: number,
    public estado?: string,
    public fechaBd?: Moment,
    public path?: string,
    public nombre?: string,
    public productoId?: number
  ) {}
}
