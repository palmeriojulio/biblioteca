import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Livro } from 'src/app/models/livro-model';
import { LivroService } from 'src/app/services/livro.service';
import { apenasLetrasValidator } from 'src/app/validators/apenas-letras.validator';
import { apenasNumerosValidator } from 'src/app/validators/apenas-numeros.validator';

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

  /**
   * Construtor do componente LivroFormComponent.
   *
   * @param livroService - Serviço para manipulação de livros.
   * @param dialogRef - Referência ao diálogo atual.
   * @param snackBar - Serviço de snack bar para exibir mensagens.
   * @param livroEdit - Dados injetados ao abrir o diálogo, para edição.
   */
  constructor(
    private livroService: LivroService, // Serviço para manipulação de livros
    public dialogRef: MatDialogRef<LivroFormComponent>, // Referência ao diálogo atual
    private snackBar: MatSnackBar, // Serviço de snack bar para exibir mensagens
    @Inject(MAT_DIALOG_DATA) public livroEdit: Livro // Dados injetados ao abrir o diálogo, para edição
  ) { }


  /**
   * Método executado ao inicializar o componente.
   * Responsável por criar o formulário do livro com uma nova instância de Livro.
   */
  ngOnInit(): void {
    this.createForm(new Livro());// Criação do formulário com um novo livro
  }

  /**
   * Cria o formulário do livro com as propriedades necessárias.
   * Se houver um livro para editar, preenche os campos do formulário com os dados do livro.
   *
   * @param livro - Dados do livro a serem preenchidos no formulário.
   */
  createForm(livro: Livro) {

    // Criação do grupo de controles do formulário com validações
    this.formLivro = new FormGroup({
      id: new FormControl(livro.id, [Validators.required, apenasNumerosValidator]),
      cdu: new FormControl(livro.cdu, [Validators.required, Validators.maxLength(15), apenasNumerosValidator]),
      titulo: new FormControl(livro.titulo, [Validators.required, Validators.maxLength(80), apenasLetrasValidator]),
      autor: new FormControl(livro.autor, [Validators.required, Validators.maxLength(80), apenasLetrasValidator]),
      editora: new FormControl(livro.editora, [Validators.required, Validators.maxLength(50), apenasLetrasValidator]),
      quantidadeDisponivel: new FormControl(livro.quantidadeDisponivel, [Validators.required, apenasNumerosValidator]),
      status: new FormControl(livro.status = "Disponível")
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
      this.formLivro.controls['quantidadeDisponivel'].setValue(this.livroEdit.quantidadeDisponivel);
      if (this.livroEdit.quantidadeDisponivel == 0) {
        this.formLivro.controls['status'].setValue(this.livroEdit.status = "Indisponível")
      }
    }
  }

  /**
   * Método chamado ao submeter o formulário.
   * Responsável por salvar ou editar um livro, dependendo do valor do botão.
   * Se o botão for "Salvar", salva um novo livro.
   * Se o botão for "Editar", atualiza o livro existente.
   * Em seguida, fecha o diálogo e exibe uma mensagem com o resultado da operação.
   */
  salvarLivro() {
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

  /**
   * Exibe uma mensagem na tela usando o snack bar.
   *
   * @param message - A mensagem a ser exibida.
   * @param action - O texto do bot o do snack bar.
   */
  open(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: this.durationInSeconds * 1000,
    });
  }

  /**
   * Fecha o di logo e retorna o valor da propriedade `result` do di logo como `undefined`.
   */
  public fecharModal() {
    this.dialogRef.close();
  }
}
