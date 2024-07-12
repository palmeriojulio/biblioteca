export class Leitor {
  id?: number;
  nome?: string;
  cpf?: string;
  rg?: string;
  dataNascimento?: string;
  telefone?: string;
  profissao?: string;
  escola?: string;
  serie?: string;
  curso?: string;
  turno?: string;
  //endereco? Endereco[];

  constructor (
    id?: number,
    nome?: string,
    cpf?: string,
    rg?: string,
    dataNascimento?: string,
    telefone?: string,
    profissao?: string,
    escola?: string,
    serie?: string,
    curso?: string,
    turno?: string
  )

  {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.rg = rg;
    this.dataNascimento = dataNascimento;
    this.telefone = telefone;
    this.profissao = profissao;
    this.escola = escola;
    this.serie = serie;
    this.curso = curso;
    this.turno = turno;
  }
}
