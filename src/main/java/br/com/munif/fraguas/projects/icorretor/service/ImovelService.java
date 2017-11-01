package br.com.munif.fraguas.projects.icorretor.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import br.com.munif.fraguas.projects.icorretor.domain.Imovel;


import br.com.munif.fraguas.projects.icorretor.service.dto.ImovelDTO;
import java.util.List;

@Service
public class ImovelService extends BaseService<Imovel>{
    
    public ImovelService(VicRepository<Imovel> repository) {
        super(repository);
    }
    
    
    
    
}