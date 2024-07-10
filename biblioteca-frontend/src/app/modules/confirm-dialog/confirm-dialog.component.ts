import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent {

  // O `dialogRef` é uma referência ao diálogo atual, permitindo que seja fechado ou manipulado
  // O `data` é injetado no diálogo e contém os dados passados quando o diálogo é aberto
  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  // Método para fechar o diálogo quando o botão "Cancelar" é clicado
  onNoClick(): void {
    // Fecha o diálogo e retorna `false` como resultado
    this.dialogRef.close();
  }
}
