package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.EmprestimoRepository;
import br.com.pjcode.biblioteca.dto.EmprestimoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository emprestimoRepository;

    public Object save(EmprestimoDto emprestimoDto) {
        return null;
    }

    public Object getAll() {
        return null;
    }

    public Object update(EmprestimoDto emprestimoDto, Long id) {
        return null;
    }
}
