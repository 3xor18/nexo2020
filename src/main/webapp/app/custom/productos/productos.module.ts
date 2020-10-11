import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductosRoutingModule } from './productos-router.module';
import { ListadoProductosComponent } from './componentes/listado/listado-productos.component';
import { CardProductoComponent } from './componentes/card-producto/card-producto.component';

/* Componentes */

@NgModule({
  declarations: [ListadoProductosComponent, CardProductoComponent],
  exports: [ListadoProductosComponent, CardProductoComponent],
  imports: [CommonModule, ProductosRoutingModule]
})
export class ProductosModule {}
