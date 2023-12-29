CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS livro(
    id_livro    BIGINT NOT NULL,
    cdu         VARCHAR(1),
    nome        VARCHAR(50),
    autor       VARCHAR(50),
    editora     VARCHAR(30),
    PRIMARY KEY (id_livro)
);

CREATE TABLE IF NOT EXISTS leitor(
    id_leitor           BIGINT NOT NULL,
    nome                VARCHAR(80),
    cpf                 VARCHAR(11),
    rg                  VARCHAR(20),
    data_nascimento     DATE,
    telefone            VARCHAR(15),
    profissao           VARCHAR(50),
    escola              VARCHAR(50),
    serie               VARCHAR(10),
    curso               VARCHAR(25),
    turno               VARCHAR(15),
    outras_informacoes  VARCHAR(50),
    PRIMARY KEY (id_leitor)
);

CREATE TABLE IF NOT EXISTS endereco(
    id_endereco     BIGINT NOT NULL,
    logradouro      VARCHAR(80),
    numero          VARCHAR(5),
    bairro          VARCHAR(80),
    cidade          VARCHAR(80),
    uf              VARCHAR(50),
    PRIMARY KEY (id_endereco)
);

CREATE TABLE IF NOT EXISTS emprestimo(
    id_emprestimo           BIGINT NOT NULL,
    livro_id_livro          BIGINT NOT NULL,
    leitor_id_leitor        BIGINT NOT NULL,
    data_do_emprestimo      TIMESTAMP WITH TIME ZONE,
    data_da_devolucao       TIMESTAMP WITH TIME ZONE,
    status_do_emprestimo    VARCHAR(50),
    PRIMARY KEY (id_emprestimo)
);

ALTER TABLE emprestimo
    ADD CONSTRAINT fk_Emprestimo_id_livro FOREIGN KEY (livro_id_livro) REFERENCES livro(id_livro);

ALTER TABLE emprestimo
    ADD CONSTRAINT fk_Emprestimo_id_leitor FOREIGN KEY (leitor_id_leitor) REFERENCES leitor(id_leitor);
