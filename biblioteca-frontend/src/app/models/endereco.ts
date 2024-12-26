export class Endereco {
  logradouro?: string;
  numero?: string;
  bairro?: string;
  cidade?: string;
  uf?: string;

  /**
   * Cria um novo objeto Endereço.
   * @param logradouro O logradouro do Endereço.
   * @param numero O número do Endereço.
   * @param bairro O bairro do Endereço.
   * @param cidade A cidade do Endereço.
   * @param uf O estado (UF) do Endereço.
   */
  constructor (
    logradouro?: string,
    numero?: string,
    bairro?: string,
    cidade?: string,
    uf?: string
  ) {
    this.logradouro = logradouro;
    this.numero = numero;
    this.bairro = bairro;
    this.cidade = cidade;
    this.uf = uf;
  }
}
