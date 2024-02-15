--
-- PostgreSQL database dump
--
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SEQUENCE livro_id_seq INCREMENT 1 START 1;
CREATE SEQUENCE leitor_id_seq INCREMENT 1 START 1;
CREATE SEQUENCE emprestimo_id_seq INCREMENT 1 START 1;
CREATE SEQUENCE endereco_id_seq INCREMENT 1 START 1;
CREATE SEQUENCE reserva_id_seq INCREMENT 1 START 1;

CREATE TABLE public.livro(
    id_livro    BIGINT NOT NULL DEFAULT NEXTVAL('leitor_id_seq'),
    cdu         VARCHAR(10),
    titulo      VARCHAR(50),
    autor       VARCHAR(50),
    editora     VARCHAR(30),
    PRIMARY KEY (id_livro)
);

CREATE TABLE public.leitor(
    id_leitor           BIGINT NOT NULL DEFAULT NEXTVAL('livro_id_seq'),
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
    id_endereco           BIGINT,
    PRIMARY KEY (id_leitor)
);

CREATE TABLE public.endereco(
    id_endereco     BIGINT NOT NULL DEFAULT NEXTVAL('endereco_id_seq'),
    logradouro      VARCHAR(80),
    numero          VARCHAR(5),
    bairro          VARCHAR(80),
    cidade          VARCHAR(80),
    uf              VARCHAR(50),
    PRIMARY KEY (id_endereco)
);

CREATE TABLE public.emprestimo(
    id_emprestimo           BIGINT NOT NULL DEFAULT NEXTVAL('emprestimo_id_seq'),
    id_livro                BIGINT NOT NULL,
    id_leitor               BIGINT NOT NULL,
    data_do_emprestimo      TIMESTAMP WITH TIME ZONE,
    data_da_devolucao       TIMESTAMP WITH TIME ZONE,
    status_do_emprestimo    VARCHAR(50),
    PRIMARY KEY (id_emprestimo)
);

CREATE TABLE public.reserva(
    id_reserva              BIGINT NOT NULL DEFAULT NEXTVAL('reserva_id_seq'),
    data_reserva            DATE,
    data_limite_reserva     DATE,
    id_livro                BIGINT NOT NULL,
    id_leitor               BIGINT NOT NULL,
    PRIMARY KEY (id_reserva)
);

-- Create Foreign Key Constraints */

ALTER TABLE emprestimo ADD CONSTRAINT fk_id_livro
    FOREIGN KEY (id_livro) REFERENCES livro (id_livro);

ALTER TABLE emprestimo ADD CONSTRAINT fk_id_leitor
    FOREIGN KEY (id_leitor) REFERENCES leitor(id_leitor);


ALTER TABLE reserva ADD CONSTRAINT fk_id_livro
    FOREIGN KEY (id_livro) REFERENCES livro(id_livro);

ALTER TABLE reserva ADD CONSTRAINT fk_id_leitor
    FOREIGN KEY (id_leitor) REFERENCES leitor(id_leitor);


ALTER TABLE leitor ADD CONSTRAINT fk_id_endereco
    FOREIGN KEY (id_endereco) REFERENCES endereco (id_endereco);