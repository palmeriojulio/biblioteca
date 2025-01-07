import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Leitor } from 'src/app/models/leitor-model';
import { LeitorService } from 'src/app/services/leitor.service';
import { LeitorFormComponent } from '../leitor-form/leitor-form.component';
import { LeitorInfoComponent } from '../leitor-info/leitor-info.component';
import { Colors } from 'chart.js';

@Component({
  selector: 'app-leitor',
  templateUrl: './leitor.component.html',
  styleUrls: ['./leitor.component.scss']
})
export class LeitorComponent implements OnInit {

  // Variáveis para construção da lista de dos leitor.
  @ViewChild(MatPaginator) paginator!: MatPaginator; // Paginator para a tabela
  @ViewChild(MatSort) sort!: MatSort; // Ordenação da tabela
  displayedColumns: string[] = ['id', 'nome', 'cpf', 'telefone', 'profissao', 'escola', 'info', 'editar', 'desativar'];
  dataSource!: MatTableDataSource<Leitor>; // Fonte de dados para a tabela
  leitor!: Leitor; // Objeto do tipo Leitor
  durationInSeconds = 5; // Duração para o snackbar

  /**
   * Construtor do componente LeitorComponent.
   *
   * @param leitorService - Serviço para comunição o com o backend.
   * @param dialog - Serviço para diálogos.
   * @param snackBar - Serviço para snackbars.
   * @param _liveAnnouncer - Serviço para acessibilidade (anuncia eventos para o usuario).
   */
  constructor(
    private leitorService: LeitorService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private _liveAnnouncer: LiveAnnouncer
  ) { }

  /**
   * Método chamado ao inicializar o componente.
   * Responsável por carregar a lista de leitores e
   * chama o método ngAfterViewInit() para configurar o sort(ordem de classificação) da
   * tabela de leitores ao iniciar o componente.
   */
  ngOnInit(): void {
    this.listarLeitores();
  }

  /**
   * Abre um diálogo para adicionar um novo leitor ou editar um existente.
   * Chama o componente LeitorFormComponent e passa o leitor atual como dado para o diálogo.
   * Atualiza a lista de leitores após o fechamento do diálogo.
   *
   * @param leitor O leitor a ser editado, ou undefined para adicionar um novo.
   */
  addLeitor(leitor: Leitor) {
    const dialogRef = this.dialog.open(LeitorFormComponent, {
      width: '800px'
    });

     dialogRef.afterClosed().subscribe(result => {
      this.listarLeitores();
    });
  }

  /**
   * Lista todos os leitores.
   * Chama o serviço para listar todos os leitores e preenche a tabela de leitores.
   * Configura o paginator e a ordenação da tabela para a tabela de leitores.
   */
  listarLeitores() {
    this.leitorService.listarLeitores().subscribe((res: any) => {
      this.dataSource = new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  /**
   * Abre um diálogo para confirmar a ativação/desativação de um leitor.
   * Determina a ação com base no estado atual do leitor.
   * Chama o serviço para ativar/desativar o leitor.
   * Atualiza a lista de leitores após a execução do serviço.
   * Exibe uma mensagem de sucesso ou erro com base no resultado da operação.
   *
   * @param leitor O leitor a ser ativar/desativado.
   */
  desativarLeitor(leitor: any) {

    const texto = leitor.ativo ? 'desativar' : 'ativar';

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: `Confirmar ${texto} o leitor`,
        message: `Deseja realmente ${texto} o leitor?`,
        titulo: `${leitor.nome}`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.leitorService.desativarLeitor(leitor.id).subscribe((res: any) => {
          this.listarLeitores();
          this.open(`Leitor ${texto}do!`, 'Fechar');
        }, (error) => {
          alert(error.error.text);
          this.listarLeitores();
        });
      }
    });
  }

  /**
   * Abre um diálogo para mostrar as informações de um leitor.
   * Chama o componente LeitorInfoComponent e passa o leitor atual como dado para o diálogo.
   * Atualiza a lista de leitores após o fechamento do diálogo.
   *
   * @param leitor O leitor a ser exibido.
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
   * Chama o componente LeitorFormComponent e passa o leitor atual como dado para o diálogo.
   * Atualiza a lista de leitores após o fechamento do diálogo.
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

  /**
   * Anuncia a mudança na ordem de classificação usando um locutor ao vivo.
   *
   * Este método é chamado sempre que o estado de classificação muda. Ele usa o
   * Serviço LiveAnnouncer para anunciar a direção de classificação atual em uma
   * forma amigável ao usuário, melhorando a acessibilidade para usuários com deficiência visual.
   *
   * @param sortState – O estado atual da classificação, incluindo o ativo
   * classificar direção. Pode ser ascendente, descendente,
   * ou nenhum (desmarcado).
   */
   announceSortChange(sortState: Sort) {
     if (sortState.direction) {
       this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
     } else {
       this._liveAnnouncer.announce('Sorting cleared');
     }
   }
}


