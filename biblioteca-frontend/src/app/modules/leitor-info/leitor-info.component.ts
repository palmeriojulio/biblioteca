import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Leitor } from 'src/app/models/leitor-model';

@Component({
  selector: 'app-leitor-info',
  templateUrl: './leitor-info.component.html',
  styleUrls: ['./leitor-info.component.scss']
})
export class LeitorInfoComponent {

  // Modelo do emprestimo injetado ao abrir o diálogo
  public leitor: Leitor = this.data

  /**
   * Constructor do componente LeitorInfoComponent.
   *
   * @param dialogRef Referência ao diálogo atual.
   * @param data Dados injetados ao abrir o diálogo, contendo as informações do leitor.
   */
  constructor(
    public dialogRef: MatDialogRef<LeitorInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  /**
   * Fecha o diálogo e retorna `false` como resultado.
   */
  onNoClick(): void {
    this.dialogRef.close();  }

}
