import { Moment } from 'moment';

export interface ICarrito {
  id?: number;
  fechaPedido?: Moment;
  fechaPago?: Moment;
  fechaConfirmadoPago?: Moment;
  fechaEntrega?: Moment;
  estado?: string;
  telefonoContacto?: string;
  whatsapp?: boolean;
  puntajeComprador?: number;
  puntajeVendedor?: number;
  fechaTermino?: Moment;
  razonTermino?: string;
  montoTotalCompra?: number;
  montoDelivery?: number;
  direccionDeliveryId?: number;
  vendedorId?: number;
  clienteId?: number;
}

export class Carrito implements ICarrito {
  constructor(
    public id?: number,
    public fechaPedido?: Moment,
    public fechaPago?: Moment,
    public fechaConfirmadoPago?: Moment,
    public fechaEntrega?: Moment,
    public estado?: string,
    public telefonoContacto?: string,
    public whatsapp?: boolean,
    public puntajeComprador?: number,
    public puntajeVendedor?: number,
    public fechaTermino?: Moment,
    public razonTermino?: string,
    public montoTotalCompra?: number,
    public montoDelivery?: number,
    public direccionDeliveryId?: number,
    public vendedorId?: number,
    public clienteId?: number
  ) {
    this.whatsapp = this.whatsapp || false;
  }
}
