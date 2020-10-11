import { Moment } from 'moment';

export interface IProducto {
  id?: number;
  nombre?: string;
  codigo?: string;
  codigoBarra?: string;
  descripcion?: string;
  cantidadDisponible?: number;
  alertaMinimo?: number;
  fechaVencimiento?: Moment;
  fechaBd?: Moment;
  estado?: string;
  precioCompraBruto?: number;
  precioVentaTotalDetal?: number;
  precioVentaTotalMayor?: number;
  unidadMedidaVendida?: number;
  precioAlmayorDespuesde?: number;
  vendedorId?: number;
  elaboradoEnId?: number;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string,
    public codigo?: string,
    public codigoBarra?: string,
    public descripcion?: string,
    public cantidadDisponible?: number,
    public alertaMinimo?: number,
    public fechaVencimiento?: Moment,
    public fechaBd?: Moment,
    public estado?: string,
    public precioCompraBruto?: number,
    public precioVentaTotalDetal?: number,
    public precioVentaTotalMayor?: number,
    public unidadMedidaVendida?: number,
    public precioAlmayorDespuesde?: number,
    public vendedorId?: number,
    public elaboradoEnId?: number
  ) {}
}
