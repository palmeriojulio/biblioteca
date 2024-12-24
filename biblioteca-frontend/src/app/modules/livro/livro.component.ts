import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Livro } from 'src/app/models/livro-model';
import { LivroService } from 'src/app/services/livro.service';
import { LivroFormComponent } from '../livro-form/livro-form.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { LivroInfoComponent } from '../livro-info/livro-info.component';

@Component({
  selector: 'app-livro',
  templateUrl: './livro.component.html',
  styleUrls: ['./livro.component.scss']
})
export class LivroComponent implements OnInit {

  // Variáveis para construção da lista de dos livros.
  @ViewChild(MatPaginator) paginator!: MatPaginator; // Paginator para a tabela
  displayedColumns: string[] = ['id', 'cdu', 'titulo', 'autor', 'quantidadeDisponivel', 'status', 'info', 'editar', 'excluir'] // Colunas exibidas na tabela
  dataSource!: MatTableDataSource<Livro>; // Fonte de dados para a tabela
  livro!: Livro; // Objeto do tipo Livro
  durationInSeconds = 5; // Duração para o snackbar

  /**
   * Construtor do componente LivroComponent.
   *
   * @param livroService - Serviço para comunicação com o backend.
   * @param dialog - Serviço para diálogos.
   * @param snackBar - Serviço para snackbars.
   */
  constructor(
    private livroService: LivroService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  /**
   * Método executado ao inicializar o componente.
   * Responsável por carregar a lista de livros ao iniciar o componente.
   */
  ngOnInit(): void {
    this.listarLivros(); // Carrega a lista de livros ao iniciar o componente
  }

  /**
   * Abre um diálogo para adicionar um novo livro ou editar um existente.
   * Chama o componente LivroFormComponent e passa o livro atual como dado para o diálogo.
   * Atualiza a lista de livros após o fechamento do diálogo.
   *
   * @param livro O livro a ser editado, ou undefined para adicionar um novo.
   */
  addLivro(livro: Livro) {
    const dialogRef = this.dialog.open(LivroFormComponent, {
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLivros();
    });
  }

  /**
   * Chama o serviço para listar os livros e popula a tabela.
   * Responsável por carregar a lista de livros ao iniciar o componente.
   */
  listarLivros() {
    this.livroService.listarLivros().subscribe((res: any) => {
      this.dataSource = new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator;
    });
  }

  /**
   * Método para deletar um livro com confirmação em diálogo.
   * Chama o serviço para deletar o livro e exibe uma mensagem de sucesso ou erro.
   * Abre um diálogo de confirmação para confirmar a exclusão.
   *
   * @param livro O livro a ser deletado.
   */
  deletarLivro(livro: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmar Exclusão',
        message: 'Deseja realmente excluir o livro?',
        titulo: `${livro.titulo}`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.livroService.deletarLivro(livro.id).subscribe((res: any) => {
          this.listarLivros();
          this.open('Livro excluído com sucesso!', 'Fechar');
        }, (error) => {
          alert(error.error.text);
          this.listarLivros();
        });
      }
    });
  }

  informacaoDoLivro(livro: any) {
    const dialogRef = this.dialog.open(LivroInfoComponent, {
      width: '800px',
      data: livro
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLivros();
    });
  }

  /**
   * Abre um diálogo para edição de um livro.
   * Chama o componente LivroFormComponent e passa o livro atual como dado para o diálogo.
   * Atualiza a lista de livros após o fechamento do diálogo.
   *
   * @param livro O livro a ser editado.
   */
  editarLivro(livro: any) {
    const dialogRef = this.dialog.open(LivroFormComponent, {
      width: '800px',
      data: livro // Passa o livro atual como dado para o diálogo
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLivros();
    });
  }

  /**
   * Exibe uma mensagem usando o snack bar.
   *
   * @param message - A mensagem a ser exibida no snack bar.
   * @param action - O texto do botão de ação no snack bar.
   */
  open(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: this.durationInSeconds * 1000,
    });
  }

  /**
   * Aplica um filtro na tabela de livros com base no valor digitado.
   * Chamado ao digitar na barra de pesquisa.
   *
   * @param event - O evento de input do formulário, usado para obter o valor do filtro.
   */
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase(); // Aplica o filtro na tabela
  }

}
