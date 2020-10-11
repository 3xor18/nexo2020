import { Moment } from 'moment';

export interface IProductoCategoria {
  id?: number;
  estado?: string;
  fechaBD?: Moment;
  productoId?: number;
  categoriaId?: number;
}

export class ProductoCategoria implements IProductoCategoria {
  constructor(
    public id?: number,
    public estado?: string,
    public fechaBD?: Moment,
    public productoId?: number,
    public categoriaId?: number
  ) {}
}
