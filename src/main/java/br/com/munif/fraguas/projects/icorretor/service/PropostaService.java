package br.com.munif.fraguas.projects.icorretor.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import br.com.munif.fraguas.projects.icorretor.domain.Proposta;


import br.com.munif.fraguas.projects.icorretor.service.dto.PropostaDTO;
import java.util.List;

@Service
public class PropostaService extends BaseService<Proposta>{
    
    public PropostaService(VicRepository<Proposta> repository) {
        super(repository);
    }
    
    
    
    
}