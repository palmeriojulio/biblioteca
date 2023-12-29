package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.LivroRepository;
import br.com.pjcode.biblioteca.domain.Livro;
import br.com.pjcode.biblioteca.dto.LivroDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class LivroService {

    final LivroRepository livroRepository;
    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    public LivroDto save(LivroDto dto) {
        try {
            var livro = livroRepository.save(LivroDto.toLivro(dto));
            return convertReturn(livro);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LivroDto update(LivroDto dto) {
        try {
            var livro = livroRepository.save(LivroDto.toLivro(dto));
            return convertReturn(livro);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<LivroDto> findAll() {
        try {
            return livroRepository.findAll()
                    .stream()
                    .map(l -> LivroDto.fromLivro(l))
                    .sorted((l1, l2) -> l1.getId().compareTo(l2.getId()))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LivroDto findById(Long id) {
        try {
            var livro = livroRepository.findById(id);
            return  convertOptionalReturn(livro);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LivroDto findByCdu(String cdu) {
        try {
            var livro = livroRepository.findByCdu(cdu);
            return convertReturn(livro);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Long id) {
        try {
            livroRepository.deleteById(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }
    }

    private LivroDto convertOptionalReturn(Optional<Livro> livro) {
        try {
            if (livro.isPresent()){
                return LivroDto.fromLivro(livro.get());
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LivroDto convertReturn(Livro livro) {
        try {
            if (Objects.nonNull(livro)) {
                return LivroDto.fromLivro(livro);
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

}
