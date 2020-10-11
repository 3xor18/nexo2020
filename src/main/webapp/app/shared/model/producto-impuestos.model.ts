import { Moment } from 'moment';

export interface IProductoImpuestos {
  id?: number;
  estado?: string;
  nombre?: string;
  fechaBd?: Moment;
  impuestoMontoFijo?: boolean;
  montoOTasa?: number;
  impuestoNacional?: boolean;
  fechaAplicable?: Moment;
  productoId?: number;
}

export class ProductoImpuestos implements IProductoImpuestos {
  constructor(
    public id?: number,
    public estado?: string,
    public nombre?: string,
    public fechaBd?: Moment,
    public impuestoMontoFijo?: boolean,
    public montoOTasa?: number,
    public impuestoNacional?: boolean,
    public fechaAplicable?: Moment,
    public productoId?: number
  ) {
    this.impuestoMontoFijo = this.impuestoMontoFijo || false;
    this.impuestoNacional = this.impuestoNacional || false;
  }
}
