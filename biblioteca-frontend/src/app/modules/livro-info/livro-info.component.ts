import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Livro } from 'src/app/models/livro-model';

@Component({
  selector: 'app-livro-info',
  templateUrl: './livro-info.component.html',
  styleUrls: ['./livro-info.component.scss']
})
export class LivroInfoComponent {

  // Modelo do emprestimo injetado ao abrir o diálogo
  public livro: Livro = this.data

  /**
   * Constructor do componente LivroInfoComponent.
   *
   * @param dialogRef Referência ao diálogo atual.
   * @param data Dados injetados ao abrir o diálogo, contendo as informações do livro.
   */
  constructor(
    public dialogRef: MatDialogRef<LivroInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  /**
   * Fecha o diálogo e retorna `false` como resultado.
   */
  onNoClick(): void {
    this.dialogRef.close();
  }
}
