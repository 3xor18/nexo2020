import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

/* Modulos */
import { ProductosModule } from './productos/productos.module';
import { CustomRoutingModule } from './custom-router.module';
import { MiTiendaModule } from './mitienda/mi-tienda.module';
import { MiCarritoModule } from './micarrito/mi-carrito.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, CustomRoutingModule, ProductosModule, MiTiendaModule, MiCarritoModule]
})
export class CustomModule {}
