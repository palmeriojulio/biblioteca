package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.constants.StatusEmprestimoEnum;
import br.com.pjcode.biblioteca.constants.StatusLivroEnum;
import br.com.pjcode.biblioteca.dao.EmprestimoRepository;
import br.com.pjcode.biblioteca.dao.LeitorRepository;
import br.com.pjcode.biblioteca.dao.LivroRepository;
import br.com.pjcode.biblioteca.domain.Emprestimo;
import br.com.pjcode.biblioteca.domain.Leitor;
import br.com.pjcode.biblioteca.domain.Livro;
import br.com.pjcode.biblioteca.dto.EmprestimoDto;
import br.com.pjcode.biblioteca.dto.LeitorDto;
import br.com.pjcode.biblioteca.dto.LivroDto;
import br.com.pjcode.biblioteca.service.exceptions.ConflictException;
import br.com.pjcode.biblioteca.service.exceptions.InternalServerErrorException;
import br.com.pjcode.biblioteca.service.exceptions.LivroIndisponivelException;
import br.com.pjcode.biblioteca.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Classe de serviço com as regras de negócio do emṕrestimo.
 * @author Palmério Júlio
 */
@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository emprestimoRepository;
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    LeitorRepository leitorRepository;

    /**
     * Método que salva um empréstimo de um livro.
     * @author Palmério Júlio
     * @param emprestimoDto
     * @return Object da classe Empréstimo.
     */
    @Transactional
    public EmprestimoDto save(EmprestimoDto emprestimoDto) {
        try {
            var leitor = regraLeitor(emprestimoDto.getLeitor().getId());
            var livros = regraLivros(emprestimoDto.getLivros());
            return regraEmprestimo(emprestimoDto, leitor, livros);
        } catch (ResourceNotFoundException | ConflictException e) {
            throw e;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Erro ao processar o empréstimo", e);
        }
    }

    /**
     * Método que busca todos os empréstimos realizados.
     * @author Palmério Júlio
     * @return List com todos os empréstimos realizados.
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object getAll() {
        try {
            return emprestimoRepository.findAll()
                    .stream()
                    .map(EmprestimoDto::fromEmprestimo)
                    .sorted((e1, e2 ) -> e1.getId().compareTo(e2.getId()))
                    .collect(Collectors.toList());
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Erro ao buscar os empréstimos!");
        }
    }

    /**
     * Método que busca todos os empréstimos realizados com status ativo.
     * @author Palmério Julio
     * @return List com todos os empréstimos realizados com status ativo.
     * @exception InternalServerErrorException
     */
    public Object getAllStatusAtivo() {
        return null;
    }

    /**
     * Método para buscar um empréstimo realizado.
     * @author Palmério Júlio
     * @param id
     * @return Object com o Empréstimo referente ao "ID" passado como parâmetro.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object findById(Long id) {
        try {
            var emprestimo = emprestimoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Empréstimo com ID: "+ id +" não encontrado!"));
            return convertReturn(emprestimo);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (InternalServerErrorException e) {
            return new InternalServerErrorException("Erro ao buscar o empréstimo!");
        }
    }

    /**
     * Método para atualizar os dados de um empréstimo.
     * @author Palmério Júlio
     * @param emprestimoDto
     * @param id
     * @return Entity de Empréstimo atualizado.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    public Object update(EmprestimoDto emprestimoDto, Long id) {
        return null;
    }

    /**
     * Método para excluído um empréstimo.
     * @author Palmério Júlio
     * @param id
     * @return Object com uma mensagem caso o empréstimo tenho sido excluído.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object delete(Long id) {
        try {
            var emprestimo = emprestimoRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Empréstimo com ID: "+ id +" não encontrado!"));
            emprestimoRepository.delete(emprestimo);
            return "Empréstimo deletado!";
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (InternalServerErrorException e) {
            return new InternalServerErrorException("Erro ao deletar o empréstimo!");
        }
    }

    /**
     * Método para regra do empréstimo, seta EMPRESTADO nos livros que forem escolhidos,
     * coloca a data atual no empréstimo e seta a data de devolução.
     * @author Palmério Júlio
     * @param emprestimoDto
     * @param leitor
     * @param livros
     * @return Dto com o empréstimo finalizado.
     */
    @Transactional
    private EmprestimoDto regraEmprestimo(EmprestimoDto emprestimoDto, Leitor leitor, List<Livro> livros) {
        try {
            //Transforma TDO para Entidade
            var emprestimo = EmprestimoDto.toEmprestimo(emprestimoDto);

            // Define os detalhes do empréstimo
            emprestimo.setLeitor(leitorRepository.findById(leitor.getId()).
                    orElseThrow(()-> new ResourceNotFoundException("Leitor com ID: "+leitor.getId()+" não encontrado!")));
            emprestimo.setLivros(livros);
            emprestimo.setDataDoEmprestimo(LocalDateTime.now());
            emprestimo.setDataDevolucaoPrevista(LocalDateTime.now().plusDays(6));
            emprestimo.setStatus(StatusEmprestimoEnum.ATIVO);

            // Salva o empréstimo no banco de dados
            return EmprestimoDto.fromEmprestimo(emprestimoRepository.save(emprestimo));
        } catch (ResourceNotFoundException | ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao gravar no banco!", e);
        }
    }

    /**
     * Finaliza um empréstimo de um livro, atualiza o status do empréstimo e dos livros,
     * e salva o empréstimo finalizado no banco de dados.
     * @param emprestimoDto
     * @param id
     * @return Dto com o empréstimo finalizado.
     * @throws ResourceNotFoundException
     * @throws ConflictException
     * @throws InternalServerErrorException
     */
    public Object finalizarEmprestimo(EmprestimoDto emprestimoDto, Long id) {

        try {
            Emprestimo emprestimo = emprestimoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado com o ID: " + id));

            // Verifica se o empréstimo já foi finalizado
            if (emprestimo.getStatus() == StatusEmprestimoEnum.CONCLUIDO) {
                throw new ConflictException("Este empréstimo já foi finalizado anteriormente.");
            }

            // Atualiza o status e a quantidade de exemplares de cada livro
            for (Livro livro : emprestimo.getLivros()) {
                livro.setStatus(StatusLivroEnum.DISPONIVEL);
                livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
                livroRepository.save(livro);
            }

            // Define a data de devolução real e atualiza o status do empréstimo
            emprestimo.setDataDevolucaoReal(LocalDateTime.now());
            emprestimo.setStatus(StatusEmprestimoEnum.CONCLUIDO);

            // Atualiza o empréstimo no banco de dados
            Emprestimo emprestimoFinalizado = emprestimoRepository.save(emprestimo);
            return EmprestimoDto.fromEmprestimo(emprestimoFinalizado);

        } catch (ResourceNotFoundException | ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao gravar no banco!", e);
        }
    }


    /**
     * Método que procura o leitor com id passado no JSON.
     * @author Palmério Júlio
     * @param leitorId
     * @return Dto de Leitor
     * @exception RuntimeException
     */
    private Leitor regraLeitor(Long leitorId) {
        try {
            // Tenta recuperar o leitor pelo ID
            return leitorRepository.findById(leitorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Leitor não encontrado com o ID: " + leitorId));
        }  catch (ResourceNotFoundException e) {
            // Lança a exceção de recurso não encontrado
            throw e;
        } catch (RuntimeException e) {
            // Captura qualquer outra exceção que ocorra
            e.printStackTrace();
            // Lança uma exceção personalizada para indicar um problema interno no servidor
            throw  new InternalServerErrorException("Erro ao buscar o livro!", e);
        }
    }


    /**
     * Método que procura os livros e adiciona a uma lista
     * @author Palmério Júlio
     * @param livrosDtos
     * @return List de livros
     * @exception RuntimeException
     */
    private List<Livro> regraLivros(List<LivroDto> livrosDtos) {
        try {
            return livrosDtos.stream()
                    .map(e -> {
                        Livro livro = livroRepository.findById(e.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com o ID: " + e.getId()));
                        if (livro.getQuantidadeDisponivel() > 0) {
                            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1); // Diminui a quantidade do livro
                            livro.setStatus(StatusLivroEnum.EMPRESTADO); // Atualiza o status do livro
                            livroRepository.save(livro); // Atualiza o livro no banco de dados
                        } else {
                            throw new ConflictException("Livro com ID: " + livro.getId() + " não está disponível para empréstimo.");
                        }
                        return livro;
                    })
                    .sorted(Comparator.comparing(Livro::getId))
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException | ConflictException e) {
            throw e;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Erro ao processar os livros", e);
        }
    }




    /**
     * Converte para retornar um DTO.
     * @author Palmério Júlio
     * @param emprestimo
     * @return Dto de empréstimo.
     * @exception RuntimeException
     */
    private Object convertReturn(Emprestimo emprestimo) {
        try {
            if (Objects.nonNull(emprestimo)) {
                return EmprestimoDto.fromEmprestimo(emprestimo);
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
