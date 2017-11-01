package br.com.munif.fraguas.projects.icorretor.service.mapper;

import br.com.munif.fraguas.projects.icorretor.domain.*;
import br.com.munif.fraguas.projects.icorretor.service.dto.CorretorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Corretor and its DTO CorretorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorretorMapper extends EntityMapper <CorretorDTO, Corretor> {
    
    
    default Corretor fromId(String id) {
        if (id == null) {
            return null;
        }
        Corretor corretor = new Corretor();
        corretor.setId(id);
        return corretor;
    }
}
