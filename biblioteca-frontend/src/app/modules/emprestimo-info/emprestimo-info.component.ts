import { Component, Inject } from '@angular/core';
import { Emprestimo } from '../../models/emprestimo';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-emprestimo-info',
  templateUrl: './emprestimo-info.component.html',
  styleUrls: ['./emprestimo-info.component.scss']
})
export class EmprestimoInfoComponent {

  // Modelo do emprestimo injetado ao abrir o diálogo
  public emprestimo: Emprestimo = this.data;

  /**
   * Constructor do componente EmprestimoInfoComponent.
   *
   * @param dialogRef Referência ao diálogo atual.
   * @param data Dados injetados ao abrir o diálogo, contendo as informações do empréstimo.
   */
  constructor(
    public dialogRef: MatDialogRef<EmprestimoInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  /**
     * Fecha o diálogo e retorna `false` como resultado.
     */
  onNoClick(): void {
    this.dialogRef.close();
  }
}
