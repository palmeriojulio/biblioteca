import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Livro } from 'src/app/models/livro-model';
import { LivroService } from 'src/app/services/livro.service';
import { LivroFormComponent } from '../livro-form/livro-form.component';

@Component({
  selector: 'app-livro',
  templateUrl: './livro.component.html',
  styleUrls: ['./livro.component.scss']
})
export class LivroComponent implements OnInit {

  // Variáveis para construção da lista de dos livros.
  @ViewChild(MatPaginator) paginator!: MatPaginator; // Paginator para a tabela
  displayedColumns: string[] = ['id', 'cdu', 'titulo', 'autor', 'quantidadeDisponivel', 'status', 'editar', 'excluir'] // Colunas exibidas na tabela
  dataSource!: MatTableDataSource<Livro>; // Fonte de dados para a tabela
  livro!: Livro; // Objeto do tipo Livro
  durationInSeconds = 5; // Duração para o snackbar

  constructor(
    private livroService: LivroService, // Serviço para comunicação com o backend
    private dialog: MatDialog, // Serviço para diálogos
    private snackBar: MatSnackBar // Serviço para snackbars
  ) { }

  ngOnInit(): void {
    this.listarLivros(); // Carrega a lista de livros ao iniciar o componente
  }

  // Método para listar livros
  listarLivros() {
    this.livroService.listarLivros().subscribe((res: any) => {
      this.dataSource = new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator; // Associa o paginator à tabela
    });
  }

  // Método para deletar um livro
  deletarLivro(id: number) {
    if (confirm("Deseja realmente excluir o livro?")) {
      this.livroService.deletarLivro(id).subscribe((res: any) => {
        // Livro deletado com sucesso
      }, (error) => {
        alert(error.error.text);
        this.listarLivros(); // Atualiza a lista de livros após a exclusão
      });
    }
  }

  // Método para abrir o formulário de edição de livro
  editarLivro(livro: any) {
    const dialogRef = this.dialog.open(LivroFormComponent, {
      width: '800px',
      data: livro // Passa o livro atual como dado para o diálogo
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLivros(); // Atualiza a lista de livros após o fechamento do diálogo
    });
  }

  // Método para abrir o formulário de criação de livro
  openDialog(livro: Livro) {
    const dialogRef = this.dialog.open(LivroFormComponent, {
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLivros(); // Atualiza a lista de livros após o fechamento do diálogo
    });
  }

  // Método para abrir um snackbar com uma mensagem
  open(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: this.durationInSeconds * 1000,
    });
  }

  // Método para aplicar filtro na tabela
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase(); // Aplica o filtro na tabela
  }

}
