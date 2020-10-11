import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPersona, Persona } from 'app/shared/model/persona.model';
import { PersonaService } from './persona.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais/pais.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-persona-update',
  templateUrl: './persona-update.component.html'
})
export class PersonaUpdateComponent implements OnInit {
  isSaving: boolean;

  pais: IPais[];

  users: IUser[];
  fechaNacDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    apellidoPaterno: [],
    apellidoMaterno: [],
    docIdentidad: [],
    fechaNac: [],
    sexo: [],
    nacionalidad: [],
    email: [],
    telefono: [],
    estado: [],
    natural: [],
    nombreComercial: [],
    scoreComprador: [],
    scoreVendedor: [],
    paisId: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected personaService: PersonaService,
    protected paisService: PaisService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ persona }) => {
      this.updateForm(persona);
    });
    this.paisService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPais[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPais[]>) => response.body)
      )
      .subscribe((res: IPais[]) => (this.pais = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(persona: IPersona) {
    this.editForm.patchValue({
      id: persona.id,
      nombre: persona.nombre,
      apellidoPaterno: persona.apellidoPaterno,
      apellidoMaterno: persona.apellidoMaterno,
      docIdentidad: persona.docIdentidad,
      fechaNac: persona.fechaNac,
      sexo: persona.sexo,
      nacionalidad: persona.nacionalidad,
      email: persona.email,
      telefono: persona.telefono,
      estado: persona.estado,
      natural: persona.natural,
      nombreComercial: persona.nombreComercial,
      scoreComprador: persona.scoreComprador,
      scoreVendedor: persona.scoreVendedor,
      paisId: persona.paisId,
      userId: persona.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const persona = this.createFromForm();
    if (persona.id !== undefined) {
      this.subscribeToSaveResponse(this.personaService.update(persona));
    } else {
      this.subscribeToSaveResponse(this.personaService.create(persona));
    }
  }

  private createFromForm(): IPersona {
    return {
      ...new Persona(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellidoPaterno: this.editForm.get(['apellidoPaterno']).value,
      apellidoMaterno: this.editForm.get(['apellidoMaterno']).value,
      docIdentidad: this.editForm.get(['docIdentidad']).value,
      fechaNac: this.editForm.get(['fechaNac']).value,
      sexo: this.editForm.get(['sexo']).value,
      nacionalidad: this.editForm.get(['nacionalidad']).value,
      email: this.editForm.get(['email']).value,
      telefono: this.editForm.get(['telefono']).value,
      estado: this.editForm.get(['estado']).value,
      natural: this.editForm.get(['natural']).value,
      nombreComercial: this.editForm.get(['nombreComercial']).value,
      scoreComprador: this.editForm.get(['scoreComprador']).value,
      scoreVendedor: this.editForm.get(['scoreVendedor']).value,
      paisId: this.editForm.get(['paisId']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersona>>) {
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

  trackPaisById(index: number, item: IPais) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
