import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Leitor } from 'src/app/models/leitor-model';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { LeitorService } from 'src/app/services/leitor.service';
import { LeitorFormComponent } from '../leitor-form/leitor-form.component';
import { LeitorInfoComponent } from '../leitor-info/leitor-info.component';

@Component({
  selector: 'app-leitor',
  templateUrl: './leitor.component.html',
  styleUrls: ['./leitor.component.scss']
})
export class LeitorComponent implements OnInit {

  // Variáveis para construção da lista de dos leitor.
  @ViewChild(MatPaginator) paginator!: MatPaginator; // Paginator para a tabela
  displayedColumns: string[] = ['id', 'nome', 'cpf', 'telefone', 'profissao', 'escola', 'info', 'editar', 'excluir'] // Colunas exibidas na tabela
  dataSource!: MatTableDataSource<Leitor>; // Fonte de dados para a tabela
  leitor!: Leitor; // Objeto do tipo Leitor
  durationInSeconds = 5; // Duração para o snackbar

  constructor(
    private leitorService: LeitorService, // Serviço para comunicação com o backend
    private dialog: MatDialog, // Serviço para diálogos
    private snackBar: MatSnackBar // Serviço para snackbars
  ) { }

  ngOnInit(): void {
    this.listarLeitores(); // Carrega a lista de leitores ao iniciar o componente
  }

  // Método para listar leitores
  listarLeitores() {
    this.leitorService.listarLeitores().subscribe((res: any) => {
      this.dataSource = new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator; // Associa o paginator à tabela
    });
  }


  /**
   * Abre um diálogo de confirmação para excluir um leitor.
   * Se confirmado, chama o serviço para deletar o leitor e exibe uma mensagem de sucesso ou erro.
   *
   * @param leitor O leitor a ser deletado.
   */
  deletarLeitor(leitor: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmar Exclusão',
        message: 'Deseja realmente excluir o leitor?',
        titulo: `${leitor.nome}`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.leitorService.deletarLeitor(leitor.id).subscribe((res: any) => {
          this.listarLeitores(); // Atualiza a lista de leitores após a exclusão
          this.open('Leitor excluído com sucesso!', 'Fechar');
        }, (error) => {
          alert(error.error.text);
          this.listarLeitores();
        });
      }
    });
  }

  informacaoDoLeitor(leitor: any) {
    const dialogRef = this.dialog.open(LeitorInfoComponent, {
      width: '800px',
      data: leitor
    });

     dialogRef.afterClosed().subscribe(result => {
      this.listarLeitores();
    });
  }

  // Método para abrir o formulário de edição de leitor
  editarLeitor(leitor: any) {
    const dialogRef = this.dialog.open(LeitorFormComponent, {
      width: '900px',
      data: leitor // Passa o leitor atual como dado para o diálogo
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLeitores(); // Atualiza a lista de leitores após o fechamento do diálogo
    });
  }

  // Método para abrir o formulário de criação de leitor
  openDialog(leitor: Leitor) {
    const dialogRef = this.dialog.open(LeitorFormComponent, {
      width: '900px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLeitores(); // Atualiza a lista de leitores após o fechamento do diálogo
    });
  }

  // Método para abrir um snackbar com uma mensagem
  open(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: this.durationInSeconds * 1000,
    });
  }

  /**
   * Aplica um filtro na tabela de leitores com base no valor digitado.
   * Chamado ao digitar na barra de pesquisa.
   *
   * @param event - O evento de input do formulário, usado para obter o valor do filtro.
   */
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase(); // Aplica o filtro na tabela
  }
}
