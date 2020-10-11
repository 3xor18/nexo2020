import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { ProductoImagenesComponent } from './producto-imagenes.component';
import { ProductoImagenesDetailComponent } from './producto-imagenes-detail.component';
import { ProductoImagenesUpdateComponent } from './producto-imagenes-update.component';
import { ProductoImagenesDeletePopupComponent, ProductoImagenesDeleteDialogComponent } from './producto-imagenes-delete-dialog.component';
import { productoImagenesRoute, productoImagenesPopupRoute } from './producto-imagenes.route';

const ENTITY_STATES = [...productoImagenesRoute, ...productoImagenesPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoImagenesComponent,
    ProductoImagenesDetailComponent,
    ProductoImagenesUpdateComponent,
    ProductoImagenesDeleteDialogComponent,
    ProductoImagenesDeletePopupComponent
  ],
  entryComponents: [ProductoImagenesDeleteDialogComponent]
})
export class NexoProductoImagenesModule {}
