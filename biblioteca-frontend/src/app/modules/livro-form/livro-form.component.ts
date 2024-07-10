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

  formLivro!: FormGroup;// Declaração do formulário do livro
  durationInSeconds = 5;// Duração da exibição do snack bar em segundos
  btn: string = "Salvar"// Texto do botão
  title: string = "Adicionar livro"// Título do formulário

  constructor(
    private livroService: LivroService, // Serviço para manipulação de livros
    public dialogRef: MatDialogRef<LivroFormComponent>, // Referência ao diálogo atual
    private snackBar: MatSnackBar, // Serviço de snack bar para exibir mensagens
    @Inject(MAT_DIALOG_DATA) public livroEdit: Livro // Dados injetados ao abrir o diálogo, para edição
  ) { }

  // Método executado quando o componente é inicializado
  ngOnInit(): void {
    this.createForm(new Livro());// Criação do formulário com um novo livro
  }

  // Método para criar o formulário do livro
  createForm(livro: Livro) {
    // Criação do grupo de controles do formulário com validações
    this.formLivro = new FormGroup({
      id: new FormControl(livro.id, Validators.required),
      cdu: new FormControl(livro.cdu, Validators.required),
      titulo: new FormControl(livro.titulo, Validators.required),
      autor: new FormControl(livro.autor, Validators.required),
      editora: new FormControl(livro.editora, Validators.required),
      quantidadeDisponivel: new FormControl(livro.quantidadeDisponivel, Validators.required),
      status: new FormControl(livro.status, Validators.required)
    });

    // Se há um livro para editar, preenche os campos do formulário com os dados do livro
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

  // Método chamado ao submeter o formulário
  onSubmit() {
    // Se não for edição, salva um novo livro
    if (this.btn != "Editar") {
      this.livroService.salvarLivro(this.formLivro.value).subscribe((res: any) => {
        if (res != null) {
          this.open('livro salvo com sucesso!', 'X');
        } else {
          this.open('Erro ao salvar o livro!', 'X');
        }
        this.fecharModal();
      })
      this.formLivro.reset(new Livro());// Reseta o formulário para um novo livro

    // Se for edição, atualiza o livro existente
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

  // Método para exibir uma mensagem usando o snack bar
  open(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: this.durationInSeconds * 1000,
    });
  }

  // Método para fechar o diálogo
  public fecharModal() {
    this.dialogRef.close();
  }


}
