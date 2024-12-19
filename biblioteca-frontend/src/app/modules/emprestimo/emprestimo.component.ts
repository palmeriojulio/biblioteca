import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Emprestimo } from 'src/app/models/emprestimo';
import { EmprestimoService } from 'src/app/services/emprestimo.service';
import { EmprestimoFormComponent } from '../emprestimo-form/emprestimo-form.component';
import { EmprestimoInfoComponent } from '../emprestimo-info/emprestimo-info.component';

@Component({
  selector: 'app-emprestimo',
  templateUrl: './emprestimo.component.html',
  styleUrls: ['./emprestimo.component.scss']
})
export class EmprestimoComponent implements OnInit {

  // Variáveis para construção da lista de dos emprestimos.
  @ViewChild(MatPaginator) paginator!: MatPaginator; // Paginator para a tabela.
  displayedColumns: string[] = ['id', 'livro', 'leitor', 'dataDoEmprestimo', 'dataDevolucaoPrevista', 'dataDevolucaoReal', 'status', 'info', 'devolucao'];
  dataSource: MatTableDataSource<Emprestimo> = new MatTableDataSource<Emprestimo>();
  emprestimo!: Emprestimo; // Objeto do tipo Emprestimo.
  durationInSeconds = 5; // Duração para o snackbar.
  mostrarInfo = false; // Variável para controlar a visibilidade do card de informação do emprestimo selecionado.

  /**
   * Construtor do componente EmprestimoComponent.
   *
   * @param emprestimoService - Serviço para manipulação de empréstimos.
   * @param dialog - Serviço para diálogos.
   * @param snackBar - Serviço para snackbars.
   */
  constructor(
    private emprestimoService: EmprestimoService, // Serviço para comunicação com o backend.
    private dialog: MatDialog, // Serviço para diálogos.
    private snackBar: MatSnackBar // Serviço para snackbars.
  ) { }

  /**
   * Método chamado ao inicializar o componente.
   * Responsável por listar os empréstimos e inicializar a tabela de dados.
   */
  ngOnInit(): void {
    this.listarEmprestimos();
  }

  /**
   * Chama o serviço para listar os empréstimos e popula a tabela.
   * Chama o método getLivrosFormatados() em cada objeto Emprestimo da lista para formatar os livros.
   */
  listarEmprestimos() {
    this.emprestimoService.listarEmprestimos().subscribe((res: any) => {
      this.dataSource = new MatTableDataSource<Emprestimo>(res);
      this.dataSource.paginator = this.paginator; // Associa o paginator à tabela

      // Chama o método getLivrosFormatados() em cada objeto Emprestimo da lista
      this.dataSource.data.forEach(emprestimo => {
        if (emprestimo instanceof Emprestimo) {
          const livrosFormatados = emprestimo.getLivrosFormatados();
        }
      });
    });
  }

  /**
   * Obtém os detalhes de um empréstimo pelo ID.
   * Chama o serviço para listar um empréstimo específico e exibe as informações.
   * @param id O ID do empréstimo a ser buscado.
   */
  listarEmprestimosById(id: number) {
    this.emprestimoService.listarEmprestimosById(id).subscribe((res: any) => {
      this.emprestimo = res;
      this.mostrarInfo = true;
      console.log('mostrarInfo:', this.mostrarInfo);
    });
  }

  /**
   * Chama o serviço para deletar um empréstimo e exibe uma mensagem de sucesso ou erro.
   * Abre um diálogo de confirmação para confirmar a exclusão.
   * @param emprestimo O emprestimo a ser deletado.
   */
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

  /**
   * Abre um diálogo para edição de um empréstimo.
   * @param emprestimo O emprestimo a ser editado.
   */
  editarEmprestimo(emprestimo: any) {
    const dialogRef = this.dialog.open(EmprestimoComponent, {
      width: '800px',
      data: emprestimo // Passa o emprestimo atual como dado para o diálogo
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarEmprestimos(); // Atualiza a lista de emprestimo após o fechamento do diálogo
    });
  }

  /**
   * Realiza a devolução de um emprestimo.
   * Chama o serviço de devolução de empréstimo e exibe uma mensagem de sucesso ou erro.
   * @param emprestimo O emprestimo a ser devolvido.
   */
  devolucao(emprestimo: any) {
    this.emprestimoService.devolucao(emprestimo.id, emprestimo).subscribe((res: any) => {
      this.listarEmprestimos(); // Atualiza a lista de emprestimo depois da devolução
      this.open('emprestimo devolvido com sucesso!', 'Fechar');
    }, (error) => {
      alert(error.error.text);
      this.listarEmprestimos();
    });
  }


  /**
   * Abre um diálogo para adicionar um novo empréstimo ou editar um existente.
   * @param emprestimo O emprestimo a ser editado, ou undefined para adicionar um novo.
   */
  openDialog(emprestimo: Emprestimo) {
    const dialogRef = this.dialog.open(EmprestimoFormComponent, {
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarEmprestimos(); // Atualiza a lista de empréstimos após o fechamento do diálogo
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
   * Aplica o filtro na tabela de emprestimos.
   * Chamado ao digitar na barra de pesquisa.
   * @param event O evento de input do formulário.
   */
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase()
  }
}
