package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.constants.StatusEmprestimoEnum;
import br.com.pjcode.biblioteca.constants.StatusLivroEnum;
import br.com.pjcode.biblioteca.dao.EmprestimoRepository;
import br.com.pjcode.biblioteca.dao.LeitorRepository;
import br.com.pjcode.biblioteca.dao.LivroRepository;
import br.com.pjcode.biblioteca.domain.Leitor;
import br.com.pjcode.biblioteca.domain.Livro;
import br.com.pjcode.biblioteca.dto.EmprestimoDto;
import br.com.pjcode.biblioteca.dto.LeitorDto;
import br.com.pjcode.biblioteca.dto.LivroDto;
import br.com.pjcode.biblioteca.service.exceptions.InternalServerErrorException;
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
    private EmprestimoDto regraEmprestimo(EmprestimoDto emprestimoDto, Leitor leitor, List<Livro> livros) {
        try {
            var emprestimo = EmprestimoDto.toEmprestimo(emprestimoDto);
            // Verificar se o livro está disponível
            // Define o status "emprestado" para cada livro na lista
            for (Livro livro : livros) {
                livro.setStatus(StatusLivroEnum.EMPRESTADO);
            }
            emprestimo.setLeitor(leitorRepository.getReferenceById(leitor.getId()));
            emprestimo.setLivros(livros);
            emprestimo.setDataDoEmprestimo(LocalDateTime.now());
            emprestimo.setDataDaDevolucao(LocalDateTime.now().plusDays(6));
            emprestimo.setStatus(StatusEmprestimoEnum.ATIVO);
            return EmprestimoDto.fromEmprestimo(emprestimoRepository.save(emprestimo));
        } catch (InternalServerErrorException e) {
            e.printStackTrace();
            return null;
        }
    }
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







    public Object getAll() {
        return null;
    }

    public Object update(EmprestimoDto emprestimoDto, Long id) {
        return null;
    }
}
