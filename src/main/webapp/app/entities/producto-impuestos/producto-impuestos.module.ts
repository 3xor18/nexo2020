import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { ProductoImpuestosComponent } from './producto-impuestos.component';
import { ProductoImpuestosDetailComponent } from './producto-impuestos-detail.component';
import { ProductoImpuestosUpdateComponent } from './producto-impuestos-update.component';
import {
  ProductoImpuestosDeletePopupComponent,
  ProductoImpuestosDeleteDialogComponent
} from './producto-impuestos-delete-dialog.component';
import { productoImpuestosRoute, productoImpuestosPopupRoute } from './producto-impuestos.route';

const ENTITY_STATES = [...productoImpuestosRoute, ...productoImpuestosPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoImpuestosComponent,
    ProductoImpuestosDetailComponent,
    ProductoImpuestosUpdateComponent,
    ProductoImpuestosDeleteDialogComponent,
    ProductoImpuestosDeletePopupComponent
  ],
  entryComponents: [ProductoImpuestosDeleteDialogComponent]
})
export class NexoProductoImpuestosModule {}
