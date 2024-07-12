import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Leitor } from 'src/app/models/leitor-model';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { LeitorService } from 'src/app/services/leitor.service';
import { LeitorFormComponent } from '../leitor-form/leitor-form.component';

@Component({
  selector: 'app-leitor',
  templateUrl: './leitor.component.html',
  styleUrls: ['./leitor.component.scss']
})
export class LeitorComponent implements OnInit {

  // Variáveis para construção da lista de dos leitor.
  @ViewChild(MatPaginator) paginator!: MatPaginator; // Paginator para a tabela
  displayedColumns: string[] = ['id', 'nome', 'telefone', 'profissao', 'escola', 'editar', 'excluir'] // Colunas exibidas na tabela
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

  // Método para deletar um leitor com confirmação em diálogo
  deletarLeitor(leitor: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmar Exclusão',
        message: 'Deseja realmente excluir o leitor?',
        titulo: `${leitor.titulo}`
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

  // Método para abrir o formulário de edição de leitor
  editarLeitor(leitor: any) {
    const dialogRef = this.dialog.open(LeitorFormComponent, {
      width: '800px',
      data: leitor // Passa o leitor atual como dado para o diálogo
    });

    dialogRef.afterClosed().subscribe(result => {
      this.listarLeitores(); // Atualiza a lista de leitores após o fechamento do diálogo
    });
  }

  // Método para abrir o formulário de criação de leitor
  openDialog(leitor: Leitor) {
    const dialogRef = this.dialog.open(LeitorFormComponent, {
      width: '800px'
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

  // Método para aplicar filtro na tabela
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase(); // Aplica o filtro na tabela
  }
}
