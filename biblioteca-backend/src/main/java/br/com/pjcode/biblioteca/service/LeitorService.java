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

    /**
     * Método para salvar um leitor no banco.
     * @author Palmério Júlio
     * @param leitorDto
     * @return Object com o Leitor cadastrado.
     * @throws ConflictException
     * @exception InternalServerErrorException
     */
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

    /**
     * Método para buscar todos os registros de Leitores já cadastrados.
     * @author Palmério Júlio
     * @return List<Leitor>
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public Object getAll() {
        try {
            return leitorRepository.findAll()
                    .stream()
                    .map(l -> LeitorDto.fromLeitor(l))
                    .sorted((l1, l2) -> l1.getId().compareTo(l2.getId()))
                    .collect(Collectors.toList());
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Erro ao buscar os leitores");
        }
    }

    /**
     * Método para buscar um leitor já cadastrado.
     * @author Palmério Júlio
     * @param id
     * @return Object com o leitor referente ao "ID" passado como parâmetro
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public Object findById(Long id) {
        try {
            var leitor = leitorRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Leitor com id: "+ id +"não encontrado!"));
            return convertReturn(leitor);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao buscar o leitor!");
        }
    }

    /**
     * Método que busca o leitor pelo CPF
     * @author Palmério Júlio
     * @param cpf
     * @return Object com o leitor referente ao "CPF" passado como parâmetro
     */
    @Transactional(readOnly = true)
    public Object findByCpf(String cpf) {
        try {
            Object leitor = leitorRepository.findByCpf(cpf);
            if (leitor == null) {
                throw new ResourceNotFoundException("Leitor com cpf: "+ cpf +" não encontrado!");
            }
            return convertReturn((Leitor) leitor);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao buscar o leitor!");
        }
    }

    /**
     * Método que pesquisa o leitor pelo nome
     * @author Palmério Júlio
     * @param nome
     * @return Object com o leitor referente ao "Nome" passado como parâmetro
     */
    @Transactional(readOnly = true)
    public Object findByName(String nome) {
        return null;
    }

    /**
     * Método para atualizar os dados de um leitor.
     * @author Palmério Júlio
     * @param leitorDto
     * @param id
     * @return Entity de leitor atualizado.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object update(LeitorDto leitorDto, Long id) {
        try {
            var leitor = leitorRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Leitor com id: "+id+" não encontrado!"));
            //procurar solução para redundância de código.
            BeanUtils.copyProperties(leitorDto.getEndereco(), leitor.getEndereco(), "id");
            BeanUtils.copyProperties(leitorDto, leitor, "id");
            return convertReturn(leitorRepository.save(leitor));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao atualizar o Leitor, ");
        }
    }

    /**
     * Método para deletar um leitor já cadastrado.
     * @author Palmério Júlio
     * @param id
     * @return Object, com uma mensagem caso o livro tenho sido deletado.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object delete(Long id) {
        try {
            var leitor = leitorRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Leitor com id: "+ id +" não encontrado!"));
            leitorRepository.deleteById(id);
            return "Leitor excluído com sucesso!";
        } catch (ResourceNotFoundException e) {
            return e.getMessage();
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao deletar o Leitor");
        }
    }

    /**
     * Método que para converter Entity em DTO.
     * @author Palmério Júlio
     * @param leitor Optional.
     * @return Dto de livro.
     */
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

    /**
     * Método que para converter Entity em DTO.
     * @author Palmério Júlio
     * @param leitor Optional.
     * @return Dto de livro.
     */
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
