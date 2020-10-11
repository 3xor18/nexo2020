import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { PersonaComponent } from './persona.component';
import { PersonaDetailComponent } from './persona-detail.component';
import { PersonaUpdateComponent } from './persona-update.component';
import { PersonaDeletePopupComponent, PersonaDeleteDialogComponent } from './persona-delete-dialog.component';
import { personaRoute, personaPopupRoute } from './persona.route';

const ENTITY_STATES = [...personaRoute, ...personaPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PersonaComponent,
    PersonaDetailComponent,
    PersonaUpdateComponent,
    PersonaDeleteDialogComponent,
    PersonaDeletePopupComponent
  ],
  entryComponents: [PersonaDeleteDialogComponent]
})
export class NexoPersonaModule {}
