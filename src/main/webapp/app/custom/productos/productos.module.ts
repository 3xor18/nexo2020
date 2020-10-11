import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductosRoutingModule } from './productos-router.module';

/* Componentes */
import { ListadoProductosComponent } from './componentes/listado-productos.component';

@NgModule({
  declarations: [ListadoProductosComponent],
  exports: [ListadoProductosComponent],
  imports: [CommonModule, ProductosRoutingModule]
})
export class ProductosModule {}
