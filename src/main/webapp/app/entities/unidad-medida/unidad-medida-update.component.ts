import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IUnidadMedida, UnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from './unidad-medida.service';

@Component({
  selector: 'jhi-unidad-medida-update',
  templateUrl: './unidad-medida-update.component.html'
})
export class UnidadMedidaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    estado: [],
    nombre: [],
    fechaBd: []
  });

  constructor(protected unidadMedidaService: UnidadMedidaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ unidadMedida }) => {
      this.updateForm(unidadMedida);
    });
  }

  updateForm(unidadMedida: IUnidadMedida) {
    this.editForm.patchValue({
      id: unidadMedida.id,
      estado: unidadMedida.estado,
      nombre: unidadMedida.nombre,
      fechaBd: unidadMedida.fechaBd != null ? unidadMedida.fechaBd.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const unidadMedida = this.createFromForm();
    if (unidadMedida.id !== undefined) {
      this.subscribeToSaveResponse(this.unidadMedidaService.update(unidadMedida));
    } else {
      this.subscribeToSaveResponse(this.unidadMedidaService.create(unidadMedida));
    }
  }

  private createFromForm(): IUnidadMedida {
    return {
      ...new UnidadMedida(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value,
      nombre: this.editForm.get(['nombre']).value,
      fechaBd: this.editForm.get(['fechaBd']).value != null ? moment(this.editForm.get(['fechaBd']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnidadMedida>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
