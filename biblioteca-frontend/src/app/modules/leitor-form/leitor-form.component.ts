import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
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

  constructor(
    private LeitorService: LeitorService, // Serviço para manipulação de leitores
    public dialogRef: MatDialogRef<LeitorFormComponent>, // Referência ao diálogo atual
    private snackBar: MatSnackBar, // Serviço de snack bar para exibir mensagens
    @Inject(MAT_DIALOG_DATA) public LeitorEdit: Leitor // Dados injetados ao abrir o diálogo, para edição
  ) { }

  // Método executado quando o componente é inicializado
  ngOnInit(): void {
    this.createForm(new Leitor());// Criação do formulário com um novo leitor
  }

  // Método para criar o formulário do leitor
  createForm(leitor: Leitor) {
    // Criação do grupo de controles do formulário com validações
    this.formLeitor = new FormGroup({
      id: new FormControl(leitor.id, Validators.required),
      nome: new FormControl(leitor.nome, [Validators.required, Validators.maxLength(80), apenasLetrasValidator]),
      cpf: new FormControl(leitor.cpf, [Validators.required, Validators.maxLength(11), apenasNumerosValidator]),
      rg: new FormControl(leitor.rg, [Validators.required, Validators.maxLength(20), apenasNumerosValidator]),
      dataNascimento: new FormControl(leitor.dataNascimento, Validators.required),
      telefone: new FormControl(leitor.telefone, [Validators.required, apenasNumerosValidator]),
      profissao: new FormControl(leitor.profissao, apenasLetrasValidator),
      escola: new FormControl(leitor.escola, [Validators.maxLength(50), apenasLetrasValidator]),
      serie: new FormControl(leitor.serie, [Validators.maxLength(10), apenasLetrasValidator]),
      curso: new FormControl(leitor.curso, [Validators.maxLength(25), apenasLetrasValidator]),
      turno: new FormControl(leitor.turno, [Validators.maxLength(15), apenasLetrasValidator])
    });

    // Se há um Leitor para editar, preenche os campos do formulário com os dados do leitor
    if (this.LeitorEdit) {
      this.btn = "Editar",
        this.title = "Editar leitor",
        this.formLeitor.controls['id'].setValue(this.LeitorEdit.id),
        this.formLeitor.controls['nome'].setValue(this.LeitorEdit.nome),
        this.formLeitor.controls['cpf'].setValue(this.LeitorEdit.cpf),
        this.formLeitor.controls['rg'].setValue(this.LeitorEdit.rg),
        this.formLeitor.controls['dataNascimento'].setValue(this.LeitorEdit.dataNascimento),
        this.formLeitor.controls['telefone'].setValue(this.LeitorEdit.telefone),
        this.formLeitor.controls['escola'].setValue(this.LeitorEdit.escola),
        this.formLeitor.controls['serie'].setValue(this.LeitorEdit.serie),
        this.formLeitor.controls['curso'].setValue(this.LeitorEdit.curso),
        this.formLeitor.controls['turno'].setValue(this.LeitorEdit.turno)

    }
  }

  // Método chamado ao submeter o formulário
  onSubmit() {
    // Se não for edição, salva um novo leitor
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

    // Se for edição, atualiza o leitor existente
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

  // Método para exibir uma mensagem usando o snack bar
  open(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: this.durationInSeconds * 1000,
    });
  }

  // Método para fechar o diálogo
  public fecharModal() {
    this.dialogRef.close();
  }
}
