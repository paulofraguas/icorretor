package br.com.munif.fraguas.projects.icorretor.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import br.com.munif.fraguas.projects.icorretor.domain.Corretor;


import br.com.munif.fraguas.projects.icorretor.service.dto.CorretorDTO;
import java.util.List;

@Service
public class CorretorService extends BaseService<Corretor>{
    
    public CorretorService(VicRepository<Corretor> repository) {
        super(repository);
    }
    
    
    
    
}