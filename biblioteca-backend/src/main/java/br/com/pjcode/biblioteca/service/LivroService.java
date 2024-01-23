package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.LivroRepository;
import br.com.pjcode.biblioteca.domain.Livro;
import br.com.pjcode.biblioteca.dto.LivroDto;
import br.com.pjcode.biblioteca.service.exceptions.EntityLivroNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public LivroDto save(LivroDto dto) throws Exception {
        try {
            Boolean retorno = livroRepository.existsByTitulo(dto.getTitulo());
            if (retorno.equals(true)){
                throw new EntityLivroNotFoundException("Já existe um livro com o titulo: "+dto.getTitulo());
            }
            var livro = livroRepository.save(LivroDto.toLivro(dto));
            return convertReturn(livro);
        } catch (RuntimeException e) {
            throw new EntityLivroNotFoundException(e.getMessage());
        }
    }
    @Transactional
    public Object update(LivroDto livroDto, Long id) {
         var livro = livroRepository.findById(id).
                 orElseThrow(() -> new EntityLivroNotFoundException("Livro com id: "+id+" não encontrado!"));
         BeanUtils.copyProperties(livroDto, livro, "id");
         livro = livroRepository.save(livro);
         return convertReturn(livro);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public LivroDto findById(Long id) {
        var livro = livroRepository.findById(id).
                orElseThrow(() -> new EntityLivroNotFoundException("Livro com id: "+id+" não encontrado!"));
        return convertReturn(livro);
    }

    @Transactional
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
