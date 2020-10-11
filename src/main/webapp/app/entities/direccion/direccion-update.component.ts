import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDireccion, Direccion } from 'app/shared/model/direccion.model';
import { DireccionService } from './direccion.service';
import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from 'app/entities/comuna/comuna.service';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona/persona.service';

@Component({
  selector: 'jhi-direccion-update',
  templateUrl: './direccion-update.component.html'
})
export class DireccionUpdateComponent implements OnInit {
  isSaving: boolean;

  comunas: IComuna[];

  personas: IPersona[];

  editForm = this.fb.group({
    id: [],
    direccion: [],
    estado: [],
    principal: [],
    comunaId: [],
    personaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected direccionService: DireccionService,
    protected comunaService: ComunaService,
    protected personaService: PersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ direccion }) => {
      this.updateForm(direccion);
    });
    this.comunaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IComuna[]>) => mayBeOk.ok),
        map((response: HttpResponse<IComuna[]>) => response.body)
      )
      .subscribe((res: IComuna[]) => (this.comunas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersona[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersona[]>) => response.body)
      )
      .subscribe((res: IPersona[]) => (this.personas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(direccion: IDireccion) {
    this.editForm.patchValue({
      id: direccion.id,
      direccion: direccion.direccion,
      estado: direccion.estado,
      principal: direccion.principal,
      comunaId: direccion.comunaId,
      personaId: direccion.personaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const direccion = this.createFromForm();
    if (direccion.id !== undefined) {
      this.subscribeToSaveResponse(this.direccionService.update(direccion));
    } else {
      this.subscribeToSaveResponse(this.direccionService.create(direccion));
    }
  }

  private createFromForm(): IDireccion {
    return {
      ...new Direccion(),
      id: this.editForm.get(['id']).value,
      direccion: this.editForm.get(['direccion']).value,
      estado: this.editForm.get(['estado']).value,
      principal: this.editForm.get(['principal']).value,
      comunaId: this.editForm.get(['comunaId']).value,
      personaId: this.editForm.get(['personaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDireccion>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackComunaById(index: number, item: IComuna) {
    return item.id;
  }

  trackPersonaById(index: number, item: IPersona) {
    return item.id;
  }
}
