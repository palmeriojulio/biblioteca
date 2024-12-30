import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Emprestimo } from 'src/app/models/emprestimo';
import { Leitor } from 'src/app/models/leitor-model';
import { LeitorService } from 'src/app/services/leitor.service';
import { Livro } from 'src/app/models/livro-model';
import { LivroService } from 'src/app/services/livro.service';
import { EmprestimoService } from 'src/app/services/emprestimo.service';

@Component({
  selector: 'app-emprestimo-form',
  templateUrl: './emprestimo-form.component.html',
  styleUrls: ['./emprestimo-form.component.scss'],
})
export class EmprestimoFormComponent implements OnInit {

  formEmprestimo!: FormGroup; // Declaração do formulário do empréstimo
  durationInSeconds = 5; // Duração da exibição do snack bar em segundos
  btn: string = 'Salvar'; // Texto do botão
  title: string = 'Realizar Empréstimo'; // Título do formulário
  livros: any;
  leitores: any;

  /**
   * Construtor do componente EmprestimoFormComponent.
   *
   * @param EmprestimoService - Serviço para manipulação de empréstimos.
   * @param dialogRef - Referência ao diálogo atual.
   * @param snackBar - Serviço de snack bar para exibir mensagens.
   * @param EmprestimoEdit - Dados injetados ao abrir o diálogo, para edição.
   */
  constructor(
    private emprestimoService: EmprestimoService, // Serviço para manipulação de empréstimos
    private leitorService: LeitorService, // Serviço para manipulação de leitores
    private livroService: LivroService, // Serviço para manipulação de livros
    public dialogRef: MatDialogRef<EmprestimoFormComponent>, // Referência ao diálogo atual
    private snackBar: MatSnackBar, // Serviço de snack bar para exibir mensagens
    @Inject(MAT_DIALOG_DATA) public EmprestimoEdit: Emprestimo // Dados injetados ao abrir o diálogo, para edição

  ) {}

  /**
   * Método executado ao inicializar o componente.
   * Responsável por criar o formulário do empréstimo com uma nova instância de Emprestimo.
   */
  ngOnInit(): void {
    // variáveis auxiliares
    const leitor = new Leitor();
    const livro = new Livro();
    const emprestimo = new Emprestimo(
      leitor, // leitor
      [livro], // livros
      '', // dataEmprestimo
      '', // dataDevolucaoPrevista
      '' // dataDevolucaoReal
    );
    this.createForm(emprestimo); // Criação do formulário com um novo empréstimo

    this.livroService.listarLivrosDisponiveis().subscribe(livros => {
      this.livros = livros;
    });

    this.leitorService.listarLeitores().subscribe(leitores => {
      this.leitores = leitores;
    });
  }

  /**
   * Cria o formulário do empréstimo com validações.
   * Se houver um empréstimo para editar, preenche os campos do formulário com os dados do empréstimo.
   * @param emprestimo O empréstimo a ser criado ou editado.
   */
  createForm(emprestimo: Emprestimo) {
    // Criação do grupo de controles do formulário com validações
    this.formEmprestimo = new FormGroup({
      id: new FormControl(emprestimo.id, Validators.required),
      leitor: new FormControl(emprestimo.leitor, Validators.required),
      livros: new FormControl(emprestimo.livros, Validators.required),
    });

    // Se há um Emprestimo para editar, preenche os campos do formulário com os dados do empréstimo
    if (this.EmprestimoEdit) {
      (this.btn = 'Editar'),
        (this.title = 'Editar empréstimo'),
        this.formEmprestimo.controls['id'].setValue(this.EmprestimoEdit.id),
        this.formEmprestimo.controls['leitor'].setValue(this.EmprestimoEdit.leitor),
        this.formEmprestimo.controls['livro'].setValue(this.EmprestimoEdit.livros),
        this.formEmprestimo.controls['dataDoEmprestimo'].setValue(this.EmprestimoEdit.dataDoEmprestimo),
        this.formEmprestimo.controls['dataDevolucaoPrevista'].setValue(this.EmprestimoEdit.dataDevolucaoPrevista),
        this.formEmprestimo.controls['dataDevolucaoReal'].setValue(this.EmprestimoEdit.dataDevolucaoReal);
      }
    }

  /**
   * Método chamado ao submeter o formulário.
   * Responsável por salvar ou editar um empréstimo, dependendo do valor do botão.
   * Se o botão for "Salvar", salva um novo empréstimo.
   * Se o botão for "Editar", atualiza o empréstimo existente.
   * Em seguida, fecha o diálogo e exibe uma mensagem com o resultado da operação.
   */
  onSubmit() {
    if (this.btn != 'Editar') {
      this.emprestimoService.salvarEmprestimo(this.formEmprestimo.value).subscribe((res: any) => {
        if (res != null) {
          this.open('Empréstimo salvo com sucesso!', 'X');
        } else {
          this.open('Erro ao realizar o empréstimo!', 'X');
        }
        this.fecharModal();
      });
      this.formEmprestimo.reset(new Leitor()); // Reseta o formulário para um novo leitor

      // Se for edição, atualiza o leitor existente
    } else {
      this.emprestimoService.editarEmprestimo(this.formEmprestimo.value).subscribe((res: any) => {
        if (res != null) {
          this.open('Empréstimo alterado com sucesso!', 'X');
        } else {
          this.open('Erro ao alterar o empréstimo!', 'X');
        }
        this.fecharModal();
      });
    }
  }

  /**
   * Função chamada ao selecionar um item na lista de livros.
   * Responsável por limitar a quantidade de livros selecionados para 3.
   * Se o usuário selecionar mais de 3 livros, este método remove o último item da lista e exibe uma mensagem de alerta.
   * @param event - O evento de seleção do item.
   */
  limitarSelecao(event: any) {
    const selcionados = event.value;
    if (selcionados.length > 3) {
      selcionados.pop();
      alert('Você só pode selecionar no máximo 3 livros.');
    }
  }

  /**
   * Método para exibir uma mensagem usando o snack bar.
   *
   * @param message - A mensagem a ser exibida.
   * @param action - O texto do botão do snack bar.
   */
  open(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: this.durationInSeconds * 1000,
    });
  }

  /**
   * Método para fechar o diálogo.
   *
   * Fecha o diálogo chamando o método `close()` do `dialogRef`.
   */
  public fecharModal() {
    this.dialogRef.close();
  }
}
