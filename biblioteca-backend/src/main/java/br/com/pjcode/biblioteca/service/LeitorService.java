package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.LeitorRepository;
import br.com.pjcode.biblioteca.dto.LeitorDto;
import br.com.pjcode.biblioteca.service.exceptions.ConflictException;
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
            Boolean retorno = LeitorRepository.(leitorDto.get);
            return null;
        } catch (ConflictException e) {

        } catch (RuntimeException e) {

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
}
