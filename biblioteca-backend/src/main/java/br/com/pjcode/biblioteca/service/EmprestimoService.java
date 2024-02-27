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
import br.com.pjcode.biblioteca.service.exceptions.InternalServerErrorException;
import br.com.pjcode.biblioteca.service.exceptions.ResourceNotFoundException;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository emprestimoRepository;
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    LeitorRepository leitorRepository;

    /**
     *
     * @param emprestimoDto
     * @return
     */
    @Transactional
    public EmprestimoDto save(EmprestimoDto emprestimoDto) {
        try {
            var leitor = regraLeitor(emprestimoDto.getLeitor());
            var livros = regraLivros(emprestimoDto.getLivros());
            return regraEmprestimo(emprestimoDto, leitor, livros);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param emprestimoDto
     * @param id
     * @return
     */
    public Object update(EmprestimoDto emprestimoDto, Long id) {
        return null;
    }

    /**
     *
     * @return
     */
    @Transactional
    public Object getAll() {
        try {
            return emprestimoRepository.findAll()
                    .stream()
                    .map(e -> EmprestimoDto.fromEmprestimo(e))
                    .sorted((e1, e2 ) -> e1.getId().compareTo(e2.getId()))
                    .collect(Collectors.toList());
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Erro ao buscar os emprestimos!");
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional
    public Object findById(Long id) {
        try {
            var emprestimo = emprestimoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Emprestimo com ID: "+ id +" não encontrado!"));
            return convertReturn(emprestimo);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (InternalServerErrorException e) {
            return new InternalServerErrorException("Erro ao buscar o emprestimo!");
        }
    }


    /**
     *
     * @param emprestimoDto
     * @param leitor
     * @param livros
     * @return
     */
    private EmprestimoDto regraEmprestimo(EmprestimoDto emprestimoDto, Leitor leitor, List<Livro> livros) {
        try {
            var emprestimo = EmprestimoDto.toEmprestimo(emprestimoDto);
            // Define o status "emprestado" para cada livro na lista.
            for (Livro livro : livros) {
                livro.setStatus(StatusLivroEnum.EMPRESTADO);
            }
            emprestimo.setLeitor(leitorRepository.getReferenceById(leitor.getId()));
            emprestimo.setLivros(livros);
            emprestimo.setDataDoEmprestimo(LocalDateTime.now());
            emprestimo.setDataDaDevolucao(LocalDateTime.now().plusDays(6));
            emprestimo.setStatus(StatusEmprestimoEnum.ATIVO.getStatus());
            return EmprestimoDto.fromEmprestimo(emprestimoRepository.save(emprestimo));
        } catch (InternalServerErrorException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param leitorDto
     * @return
     */
    private Leitor regraLeitor(LeitorDto leitorDto) {
        try {
            if (Objects.isNull(leitorDto.getId())) {
                return leitorRepository.save(LeitorDto.toLeitor(leitorDto));
            } else {
                return LeitorDto.toLeitor(leitorDto);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param livrosDtos
     * @return
     */
    private List<Livro> regraLivros(List<LivroDto> livrosDtos) {
        try {
            return livrosDtos.stream()
                    .map(e -> livroRepository.findById(e.getId()).get())
                    .sorted((e1,e2) -> e1.getId().compareTo(e2.getId()))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param emprestimo
     * @return
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
