import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { CarritoComponent } from './carrito.component';
import { CarritoDetailComponent } from './carrito-detail.component';
import { CarritoUpdateComponent } from './carrito-update.component';
import { CarritoDeletePopupComponent, CarritoDeleteDialogComponent } from './carrito-delete-dialog.component';
import { carritoRoute, carritoPopupRoute } from './carrito.route';

const ENTITY_STATES = [...carritoRoute, ...carritoPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CarritoComponent,
    CarritoDetailComponent,
    CarritoUpdateComponent,
    CarritoDeleteDialogComponent,
    CarritoDeletePopupComponent
  ],
  entryComponents: [CarritoDeleteDialogComponent]
})
export class NexoCarritoModule {}
