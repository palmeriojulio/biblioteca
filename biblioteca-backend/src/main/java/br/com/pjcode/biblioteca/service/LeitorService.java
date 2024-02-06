package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.LeitorRepository;
import br.com.pjcode.biblioteca.domain.Leitor;
import br.com.pjcode.biblioteca.dto.LeitorDto;
import br.com.pjcode.biblioteca.service.exceptions.ConflictException;
import br.com.pjcode.biblioteca.service.exceptions.InternalServerErrorException;
import br.com.pjcode.biblioteca.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Palmério Júlio
 * Classe serviço com o CRUD da entidade Leitor, trata regras de negócio, exeções e se comunica com o repositório.
 */
@Service
public class LeitorService {

    @Autowired
    LeitorRepository leitorRepository;
    @Transactional
    public Object save(LeitorDto leitorDto) throws ConflictException {
        try {
            Boolean retorno = leitorRepository.existsByCpf(leitorDto.getCpf());
            if (retorno) {
                throw new ConflictException("Já existe um leitor com o CPF: "+leitorDto.getCpf());
            }
            var leitor = leitorRepository.save(LeitorDto.toLeitor(leitorDto));
            return convertReturn(leitor);
        } catch (ConflictException e) {
            throw e;
        } catch (RuntimeException e) {
            return new InternalServerErrorException("Erro ao cadastrar o leitor, entre em contato com o suporte");
        }
    }
    @Transactional
    public Object update(LeitorDto leitorDto, Long id) {
        try {
            var leitor = leitorRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Leitor com id: "+id+" não encontrado!"));
            BeanUtils.copyProperties(leitorDto, leitor, "id");
            leitor = leitorRepository.save(leitor);
            return convertReturn(leitor);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao atualizar o Leitor, ");
        }
    }
    @Transactional(readOnly = true)
    public Object getAll() {
        try {
            return leitorRepository.findAll()
                    .stream()
                    .map(l -> LeitorDto.fromLeitor(l))
                    .sorted((l1, l2) -> l1.getId().compareTo(l2.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os leitores");
        }
    }

    public Object findById(Long id) {
        try {
            var leitor = leitorRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Livro com id: "+ id +"não encontrado!"));
            return convertReturn(leitor);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao buscar o leitor!");
        }
    }

    public Object delete(Long id) {
        try {
            if (leitorRepository.findById(id).isEmpty()) {
                throw new ResourceNotFoundException("Leitor com id: "+id+" não encontrado!");
            }
            leitorRepository.deleteById(id);
            return "Leitor excluído com sucesso!";
        } catch (ResourceNotFoundException e) {
            return e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao deletar o Leitor");
        }
    }

    private LeitorDto convertOptionalReturn(Optional<Leitor> leitor) {
        try {
            if (leitor.isPresent()){
                return LeitorDto.fromLeitor(leitor.get());
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LeitorDto convertReturn(Leitor leitor) {
        try {
            if (Objects.nonNull(leitor)) {
                return LeitorDto.fromLeitor(leitor);
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
