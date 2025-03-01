import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Endereco } from 'src/app/models/endereco';
import { Leitor } from 'src/app/models/leitor-model';
import { LeitorService } from 'src/app/services/leitor.service';
import { apenasLetrasValidator } from 'src/app/validators/apenas-letras.validator';
import { apenasNumerosValidator } from 'src/app/validators/apenas-numeros.validator';

@Component({
  selector: 'app-leitor-form',
  templateUrl: './leitor-form.component.html',
  styleUrls: ['./leitor-form.component.scss']
})
export class LeitorFormComponent implements OnInit {

  formLeitor!: FormGroup;// Declaração do formulário do leitor
  durationInSeconds = 5;// Duração da exibição do snack bar em segundos
  btn: string = "Salvar"// Texto do botão
  title: string = "Adicionar Leitor"// Título do formulário
  endereco = new Endereco();

  /**
   * Construtor do componente LeitorFormComponent.
   *
   * @param LeitorService - Serviço para manipulação de leitores.
   * @param dialogRef - Referência ao diálogo atual.
   * @param snackBar - Serviço de snack bar para exibir mensagens.
   * @param LeitorEdit - Dados injetados ao abrir o diálogo, para edição.
   */
  constructor(
    private LeitorService: LeitorService, // Serviço para manipulação de leitores
    public dialogRef: MatDialogRef<LeitorFormComponent>, // Referência ao diálogo atual
    private snackBar: MatSnackBar, // Serviço de snack bar para exibir mensagens
    @Inject(MAT_DIALOG_DATA) public LeitorEdit: Leitor // Dados injetados ao abrir o diálogo, para edição
  ) { }

  /**
   * Método executado ao inicializar o componente.
   * Responsável por criar o formulário do leitor com uma nova instância de Leitor.
   */
  ngOnInit(): void {
    this.createForm(new Leitor());// Criação do formulário com um novo leitor
  }


  /**
   * Cria o formulário do leitor com as propriedades necessárias.
   * Se houver um leitor para editar, preenche os campos do formulário com os dados do leitor.
   *
   * @param leitor - Dados do leitor a serem preenchidos no formulário.
   */
  createForm(leitor: Leitor) {
    // Criação do grupo de controles do formulário com validações
    this.formLeitor = new FormGroup({
      id: new FormControl(leitor.id, Validators.required),
      nome: new FormControl(leitor.nome, [Validators.required, apenasLetrasValidator]),
      cpf: new FormControl(leitor.cpf, [Validators.required, Validators.maxLength(14), apenasNumerosValidator]),
      rg: new FormControl(leitor.rg, [Validators.required, Validators.maxLength(20), apenasNumerosValidator]),
      dataNascimento: new FormControl(leitor.dataNascimento),
      telefone: new FormControl(leitor.telefone, [Validators.required, apenasNumerosValidator]),
      profissao: new FormControl(leitor.profissao, [apenasLetrasValidator]),
      escola: new FormControl(leitor.escola, [Validators.maxLength(30), apenasLetrasValidator]),
      serie: new FormControl(leitor.serie, [Validators.maxLength(10), apenasLetrasValidator]),
      curso: new FormControl(leitor.curso, [Validators.maxLength(15), apenasLetrasValidator]),
      turno: new FormControl(leitor.turno, [Validators.maxLength(15), apenasLetrasValidator]),
      ativo: new FormControl(leitor.ativo = "true"),
      endereco: new FormGroup ({
        logradouro: new FormControl(leitor.endereco?.logradouro, [Validators.required, apenasLetrasValidator]),
        numero: new FormControl(leitor.endereco?.numero, [Validators.required,apenasNumerosValidator]),
        bairro: new FormControl(leitor.endereco?.bairro, [Validators.required,apenasLetrasValidator]),
        cidade: new FormControl(leitor.endereco?.cidade, [Validators.required,apenasLetrasValidator]),
        uf: new FormControl(leitor.endereco?.uf, [Validators.required, apenasLetrasValidator]),
      }),
    });

    // Se há um Leitor para editar, preenche os campos do formulário com os dados do leitor
    if (this.LeitorEdit) {

        this.btn = "Editar",
        this.title = "Editar leitor",

        this.formLeitor.patchValue({
          id: this.LeitorEdit.id,
          nome: this.LeitorEdit.nome,
          cpf: this.LeitorEdit.cpf,
          rg: this.LeitorEdit.rg,
          dataNascimento: this.LeitorEdit.dataNascimento,
          telefone: this.LeitorEdit.telefone,
          profissao: this.LeitorEdit.profissao,
          escola: this.LeitorEdit.escola,
          serie: this.LeitorEdit.serie,
          curso: this.LeitorEdit.curso,
          turno: this.LeitorEdit.turno,
          ativo: this.LeitorEdit.ativo,
          endereco: {
            logradouro: this.LeitorEdit.endereco?.logradouro,
            numero: this.LeitorEdit.endereco?.numero,
            bairro: this.LeitorEdit.endereco?.bairro,
            cidade: this.LeitorEdit.endereco?.cidade,
            uf: this.LeitorEdit.endereco?.uf,
          }
        });
    }
  }

  /**
   * Método executado ao submeter o formulário.
   * Responsável por chamar o método para salvar ou editar um leitor, dependendo do valor da variável btn.
   * Se btn for diferente de "Editar", salva um novo leitor.
   * Se btn for "Editar", atualiza o leitor existente.
   * Em seguida, fecha o diálogo e reseta o formulário para um novo leitor.
   */
  salvarLeitor() {
    if (this.btn != "Editar") {
      this.LeitorService.salvarLeitor(this.formLeitor.value).subscribe((res: any) => {
        if (res != null) {
          this.open('Leitor salvo com sucesso!', 'X');
        } else {
          this.open('Erro ao salvar o leitor!', 'X');
        }
        this.fecharModal();
      })
      this.formLeitor.reset(new Leitor());// Reseta o formulário para um novo leitor

    } else {
      this.LeitorService.editarLeitor(this.formLeitor.value).subscribe((res: any) => {
        if (res != null) {
          this.open('Leitor alterado com sucesso!', 'X');
        } else {
          this.open('Erro ao alterar o leitor!', 'X');
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
