import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { CarritoProductoComponent } from './carrito-producto.component';
import { CarritoProductoDetailComponent } from './carrito-producto-detail.component';
import { CarritoProductoUpdateComponent } from './carrito-producto-update.component';
import { CarritoProductoDeletePopupComponent, CarritoProductoDeleteDialogComponent } from './carrito-producto-delete-dialog.component';
import { carritoProductoRoute, carritoProductoPopupRoute } from './carrito-producto.route';

const ENTITY_STATES = [...carritoProductoRoute, ...carritoProductoPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CarritoProductoComponent,
    CarritoProductoDetailComponent,
    CarritoProductoUpdateComponent,
    CarritoProductoDeleteDialogComponent,
    CarritoProductoDeletePopupComponent
  ],
  entryComponents: [CarritoProductoDeleteDialogComponent]
})
export class NexoCarritoProductoModule {}
