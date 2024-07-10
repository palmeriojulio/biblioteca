import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Livro } from 'src/app/models/livro-model';
import { LivroService } from 'src/app/services/livro.service';

@Component({
  selector: 'app-livro-form',
  templateUrl: './livro-form.component.html',
  styleUrls: ['./livro-form.component.scss']
})
export class LivroFormComponent implements OnInit {

  formLivro!: FormGroup;
  durationInSeconds = 5;
  btn: string = "Salvar"
  title: string = "Adicionar livro"

  constructor(
    private livroService: LivroService,
    public dialogRef: MatDialogRef<LivroFormComponent>,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public livroEdit: Livro
  ) { }

  ngOnInit(): void {
    this.createForm(new Livro());
  }

  createForm(livro: Livro) {

    this.formLivro = new FormGroup({
      id: new FormControl(livro.id, Validators.required),
      cdu: new FormControl(livro.cdu, Validators.required),
      titulo: new FormControl(livro.titulo, Validators.required),
      autor: new FormControl(livro.autor, Validators.required),
      editora: new FormControl(livro.editora, Validators.required),
      quantidadeDisponivel: new FormControl(livro.quantidadeDisponivel, Validators.required),
      status: new FormControl(livro.status, Validators.required)
    });

    if (this.livroEdit) {
      this.btn = "Editar",
        this.title = "Editar livro",
        this.formLivro.controls['id'].setValue(this.livroEdit.id),
        this.formLivro.controls['cdu'].setValue(this.livroEdit.cdu),
        this.formLivro.controls['titulo'].setValue(this.livroEdit.titulo),
        this.formLivro.controls['autor'].setValue(this.livroEdit.autor),
        this.formLivro.controls['editora'].setValue(this.livroEdit.editora),
        this.formLivro.controls['quantidadeDisponivel'].setValue(this.livroEdit.quantidadeDisponivel),
        this.formLivro.controls['status'].setValue(this.livroEdit.status)

    }
  }

  onSubmit() {

    if (this.btn != "Editar") {
      this.livroService.salvarLivro(this.formLivro.value).subscribe((res: any) => {
        if (res != null) {
          this.open('livro salvo com sucesso!', 'X');
        } else {
          this.open('Erro ao salvar o livro!', 'X');
        }
        this.fecharModal();
      })
      this.formLivro.reset(new Livro());

    } else {
      this.livroService.editarLivro(this.formLivro.value).subscribe((res: any) => {
        if (res != null) {
          this.open('livro alterado com sucesso!', 'X');
        } else {
          this.open('Erro ao alterar o livro!', 'X');
        }
        this.fecharModal();
      })
    }
  }

  open(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: this.durationInSeconds * 1000,
    });
  }

  public fecharModal() {
    this.dialogRef.close();
  }


}
