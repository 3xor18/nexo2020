import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { DireccionComponent } from './direccion.component';
import { DireccionDetailComponent } from './direccion-detail.component';
import { DireccionUpdateComponent } from './direccion-update.component';
import { DireccionDeletePopupComponent, DireccionDeleteDialogComponent } from './direccion-delete-dialog.component';
import { direccionRoute, direccionPopupRoute } from './direccion.route';

const ENTITY_STATES = [...direccionRoute, ...direccionPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DireccionComponent,
    DireccionDetailComponent,
    DireccionUpdateComponent,
    DireccionDeleteDialogComponent,
    DireccionDeletePopupComponent
  ],
  entryComponents: [DireccionDeleteDialogComponent]
})
export class NexoDireccionModule {}
