package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.LeitorRepository;
import br.com.pjcode.biblioteca.domain.Leitor;
import br.com.pjcode.biblioteca.dto.LeitorDto;
import br.com.pjcode.biblioteca.service.exceptions.ConflictException;
import br.com.pjcode.biblioteca.service.exceptions.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author Palmério Júlio
 * Classe serviço com o CRUD da entidade Leitor, trata regras de negócio, exeções e se comunica com o repositório.
 */
@Service
public class LeitorService {

    @Autowired
    LeitorRepository leitorRepository;

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

    public Object getAll() {
        return null;
    }

    public Object update(LeitorDto leitorDto, Long id) {
        return null;
    }

    public Object findById(Long id) {
        return null;
    }

    public Object delete(Long id) {
        return null;
    }

    private Object convertReturn(Leitor leitor) {
        return null;
    }
}
