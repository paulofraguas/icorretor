package br.com.munif.fraguas.projects.icorretor.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import br.com.munif.fraguas.projects.icorretor.domain.Foto;


import br.com.munif.fraguas.projects.icorretor.service.dto.FotoDTO;
import java.util.List;

@Service
public class FotoService extends BaseService<Foto>{
    
    public FotoService(VicRepository<Foto> repository) {
        super(repository);
    }
    
    
    
    
}