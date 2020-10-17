import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductosRoutingModule } from './productos-router.module';
import { ListadoProductosComponent } from './componentes/listado/listado-productos.component';
import { CardProductoComponent } from './componentes/card-producto/card-producto.component';
import { NexoSharedModule } from '../../shared/shared.module';
import { ModalAgregarProductoComponent } from './componentes/modal-agregar/modal-agregar-producto.component';

/* Componentes */

@NgModule({
  declarations: [ListadoProductosComponent, CardProductoComponent, ModalAgregarProductoComponent],
  exports: [ListadoProductosComponent, CardProductoComponent, ModalAgregarProductoComponent],
  imports: [NexoSharedModule, CommonModule, ProductosRoutingModule]
})
export class ProductosModule {}
