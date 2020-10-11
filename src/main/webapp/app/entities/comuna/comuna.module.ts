import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { ComunaComponent } from './comuna.component';
import { ComunaDetailComponent } from './comuna-detail.component';
import { ComunaUpdateComponent } from './comuna-update.component';
import { ComunaDeletePopupComponent, ComunaDeleteDialogComponent } from './comuna-delete-dialog.component';
import { comunaRoute, comunaPopupRoute } from './comuna.route';

const ENTITY_STATES = [...comunaRoute, ...comunaPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ComunaComponent, ComunaDetailComponent, ComunaUpdateComponent, ComunaDeleteDialogComponent, ComunaDeletePopupComponent],
  entryComponents: [ComunaDeleteDialogComponent]
})
export class NexoComunaModule {}
