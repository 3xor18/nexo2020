import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductosRoutingModule } from './productos-router.module';
import { ListadoProductosComponent } from './componentes/listado/listado-productos.component';
import { CardProductoComponent } from './componentes/card-producto/card-producto.component';
import { NexoSharedModule } from '../../shared/shared.module';
import { ModalAgregarProductoComponent } from './componentes/modal-agregar/modal-agregar-producto.component';
import { DatosBasicosComponent } from './componentes/modal-agregar/datos-basicos/datos-basicos.component';
import { DatosDeliveryComponent } from './componentes/modal-agregar/datos-delivery/datos-delivery.component';
import { DatosImpuestosComponent } from './componentes/modal-agregar/datos-impuestos/datos-impuestos.component';

/* Componentes */

@NgModule({
  declarations: [
    ListadoProductosComponent,
    CardProductoComponent,
    ModalAgregarProductoComponent,
    DatosBasicosComponent,
    DatosDeliveryComponent,
    DatosImpuestosComponent
  ],
  exports: [
    ListadoProductosComponent,
    CardProductoComponent,
    ModalAgregarProductoComponent,
    DatosBasicosComponent,
    DatosDeliveryComponent,
    DatosImpuestosComponent
  ],
  imports: [NexoSharedModule, CommonModule, ProductosRoutingModule]
})
export class ProductosModule {}
