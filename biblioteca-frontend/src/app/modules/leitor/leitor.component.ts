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
  displayedColumns: string[] = ['id', 'nome', 'cpf', 'telefone', 'profissao', 'escola', 'info', 'editar', 'desativar'] // Colunas exibidas na tabela
  dataSource!: MatTableDataSource<Leitor>; // Fonte de dados para a tabela
  leitor!: Leitor; // Objeto do tipo Leitor
  durationInSeconds = 5; // Duração para o snackbar

  /**
   * Construtor do componente LeitorComponent.
   *
   * @param leitorService - Serviço para comunição o com o backend.
   * @param dialog - Serviço para diálogos.
   * @param snackBar - Serviço para snackbars.
   */
  constructor(
    private leitorService: LeitorService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  /**
   * Método executado ao inicializar o componente.
   * Responsável por carregar a lista de leitores ao iniciar o componente.
   */
  ngOnInit(): void {
    this.listarLeitores(); // Carrega a lista de leitores ao iniciar o componente
  }

  /**
   * Chama o serviço para listar os leitores e popula a tabela.
   * Responsável por carregar a lista de leitores ao iniciar o componente.
   */
  listarLeitores() {
    this.leitorService.listarLeitores().subscribe((res: any) => {
      this.dataSource = new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator; // Associa o paginator à tabela
    });
  }

  /**
   * Chama o serviço para excluir um leitor e atualiza a lista de leitores.
   * Responsável por excluir um leitor.
   *
   * @param leitor - O leitor a ser excluído.
   */
  desativarLeitor(leitor: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmar inativação',
        message: 'Deseja realmente inativar o leitor?',
        titulo: `${leitor.nome}`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.leitorService.desativarLeitor(leitor.id).subscribe((res: any) => {
          this.listarLeitores();
          this.open('Leitor ativo!', 'Fechar');
        }, (error) => {
          alert(error.error.text);
          this.listarLeitores();
        });
      }
    });
  }

  /**
   * Abre um diálogo com as informações do leitor.
   * Responsável por chamar o diálogo de informações do leitor.
   *
   * @param leitor - O leitor a ter suas informações exibidas.
   */
  informacaoDoLeitor(leitor: any) {
    const dialogRef = this.dialog.open(LeitorInfoComponent, {
      width: '800px',
      data: leitor
    });

     dialogRef.afterClosed().subscribe(result => {
      this.listarLeitores();
    });
  }


  /**
   * Abre um diálogo para edição de um leitor.
   * Responsável por chamar o diálogo de edição do leitor.
   *
   * @param leitor - O leitor a ser editado.
   */
  editarLeitor(leitor: any) {
    const dialogRef = this.dialog.open(LeitorFormComponent, {
      width: '800px',
      data: leitor
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLeitores();
    });
  }

  /**
   * Abre um diálogo para cadastro de um leitor.
   * Responsável por chamar o diálogo de cadastro do leitor.
   *
   * @param leitor - O leitor a ser cadastrado.
   */
  openDialog(leitor: Leitor) {
    const dialogRef = this.dialog.open(LeitorFormComponent, {
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLeitores();
    });
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
   * Aplica um filtro na tabela de leitores com base no valor digitado.
   * Chamado ao digitar na barra de pesquisa.
   *
   * @param event - O evento de input do formulário, usado para obter o valor do filtro.
   */
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
