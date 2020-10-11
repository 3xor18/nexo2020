import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { UnidadMedidaComponent } from './unidad-medida.component';
import { UnidadMedidaDetailComponent } from './unidad-medida-detail.component';
import { UnidadMedidaUpdateComponent } from './unidad-medida-update.component';
import { UnidadMedidaDeletePopupComponent, UnidadMedidaDeleteDialogComponent } from './unidad-medida-delete-dialog.component';
import { unidadMedidaRoute, unidadMedidaPopupRoute } from './unidad-medida.route';

const ENTITY_STATES = [...unidadMedidaRoute, ...unidadMedidaPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UnidadMedidaComponent,
    UnidadMedidaDetailComponent,
    UnidadMedidaUpdateComponent,
    UnidadMedidaDeleteDialogComponent,
    UnidadMedidaDeletePopupComponent
  ],
  entryComponents: [UnidadMedidaDeleteDialogComponent]
})
export class NexoUnidadMedidaModule {}
