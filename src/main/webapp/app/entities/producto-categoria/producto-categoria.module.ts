import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { ProductoCategoriaComponent } from './producto-categoria.component';
import { ProductoCategoriaDetailComponent } from './producto-categoria-detail.component';
import { ProductoCategoriaUpdateComponent } from './producto-categoria-update.component';
import {
  ProductoCategoriaDeletePopupComponent,
  ProductoCategoriaDeleteDialogComponent
} from './producto-categoria-delete-dialog.component';
import { productoCategoriaRoute, productoCategoriaPopupRoute } from './producto-categoria.route';

const ENTITY_STATES = [...productoCategoriaRoute, ...productoCategoriaPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoCategoriaComponent,
    ProductoCategoriaDetailComponent,
    ProductoCategoriaUpdateComponent,
    ProductoCategoriaDeleteDialogComponent,
    ProductoCategoriaDeletePopupComponent
  ],
  entryComponents: [ProductoCategoriaDeleteDialogComponent]
})
export class NexoProductoCategoriaModule {}
