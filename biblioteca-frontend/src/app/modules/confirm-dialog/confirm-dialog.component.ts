import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent {

  /**
   * Construtor do componente `ConfirmDialogComponent`.
   *
   * @param dialogRef Referência ao diálogo atual.
   * @param data Dados injetados ao abrir o diálogo, contendo o título e a mensagem
   * a serem exibidos.
   */
  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  /**
   * Fecha o diálogo e retorna `false` como resultado.
   */
  onNoClick(): void {
    this.dialogRef.close();
  }
}
