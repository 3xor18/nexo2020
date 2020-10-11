export interface ICarritoProducto {
  id?: number;
  cantidad?: number;
  precioCompra?: number;
  estado?: string;
  costoDelivery?: number;
  productoId?: number;
  carritoId?: number;
}

export class CarritoProducto implements ICarritoProducto {
  constructor(
    public id?: number,
    public cantidad?: number,
    public precioCompra?: number,
    public estado?: string,
    public costoDelivery?: number,
    public productoId?: number,
    public carritoId?: number
  ) {}
}
