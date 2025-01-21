-- Altera a coluna data_emprestimo para aceitar data e hora
ALTER TABLE public.emprestimo
ALTER COLUMN data_emprestimo TYPE TIMESTAMP;

-- Altera data_devolucao_prevista para aceitar data e hora
ALTER TABLE public.emprestimo
ALTER COLUMN data_devolucao_prevista TYPE TIMESTAMP;

-- Alterar data_devolucao_real para aceitar data e hora
ALTER TABLE public.emprestimo
ALTER COLUMN data_devolucao_real TYPE TIMESTAMP;