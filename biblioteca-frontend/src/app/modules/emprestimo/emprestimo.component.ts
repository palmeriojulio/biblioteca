import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Emprestimo } from 'src/app/models/emprestimo';
import { EmprestimoService } from 'src/app/services/emprestimo.service';
import { EmprestimoFormComponent } from '../emprestimo-form/emprestimo-form.component';

@Component({
  selector: 'app-emprestimo',
  templateUrl: './emprestimo.component.html',
  styleUrls: ['./emprestimo.component.scss']
})
export class EmprestimoComponent implements OnInit {

  // Variáveis para construção da lista de dos emprestimos.
  @ViewChild(MatPaginator) paginator!: MatPaginator; // Paginator para a tabela
  displayedColumns: string[] = ['id', 'livro', 'leitor', 'dataDoEmprestimo', 'dataDevolucaoPrevista', 'dataDevolucaoReal', 'status', 'info', 'devolucao'];
  dataSource: MatTableDataSource<Emprestimo> = new MatTableDataSource<Emprestimo>();
  emprestimo!: Emprestimo; // Objeto do tipo Emprestimo
  durationInSeconds = 5; // Duração para o snackbar

  constructor(
    private emprestimoService: EmprestimoService, // Serviço para comunicação com o backend
    private dialog: MatDialog, // Serviço para diálogos
    private snackBar: MatSnackBar // Serviço para snackbars
  ) { }

  ngOnInit(): void {
    this.listarEmprestimos(); // Chama o metodo para listar os emprestimos
  }

  // Metodo para listar os emprestimos
  listarEmprestimos() {
    this.emprestimoService.listarEmprestimos().subscribe((res: any) => {
      this.dataSource = new MatTableDataSource<Emprestimo>(res);
      this.dataSource.paginator = this.paginator; // Associa o paginator à tabela

      // Chama o método getLivrosFormatados() em cada objeto Emprestimo da lista
      this.dataSource.data.forEach(emprestimo => {
        if (emprestimo instanceof Emprestimo) {
          const livrosFormatados = emprestimo.getLivrosFormatados();
          // Additional processing can be done here with livrosFormatados
        }
      });
    });
  }

  // Método para deletar um emprestimo com confirmação em diálogo
  deletarEmprestimo(emprestimo: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmar Exclusão',
        message: 'Deseja realmente excluir o emprestimo?',
        titulo: `${emprestimo.id}`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.emprestimoService.excluirEmprestimo(emprestimo.id).subscribe((res: any) => {
          this.listarEmprestimos(); // Atualiza a lista de emprestimo após a exclusão
          this.open('emprestimo excluído com sucesso!', 'Fechar');
        }, (error) => {
          alert(error.error.text);
          this.listarEmprestimos();
        });
      }
    });
  }

  // Método para abrir o formulário de edição de emprestimo
  editarEmprestimo(emprestimo: any) {
    const dialogRef = this.dialog.open(EmprestimoComponent, {
      width: '800px',
      data: emprestimo // Passa o emprestimo atual como dado para o diálogo
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarEmprestimos(); // Atualiza a lista de emprestimo após o fechamento do diálogo
    });
  }

  devolucao(emprestimo: any) {
    this.emprestimoService.devolucao(emprestimo.id, emprestimo).subscribe((res: any) => {
      this.listarEmprestimos(); // Atualiza a lista de emprestimo depois da devolução
      this.open('emprestimo devolvido com sucesso!', 'Fechar');
    }, (error) => {
      alert(error.error.text);
      this.listarEmprestimos();
    });
  }

  // Método para abrir o formulário de criação de emprestimo
  openDialog(emprestimo: Emprestimo) {
    const dialogRef = this.dialog.open(EmprestimoFormComponent, {
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarEmprestimos(); // Atualiza a lista de emprestimoes após o fechamento do diálogo
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
